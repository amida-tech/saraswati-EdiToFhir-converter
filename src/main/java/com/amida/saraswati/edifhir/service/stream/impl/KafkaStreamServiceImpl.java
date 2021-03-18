package com.amida.saraswati.edifhir.service.stream.impl;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.model.streammessage.EdiFhirMessage;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * It implements the service for kafka stream.
 *
 * @author warren lin
 */
@Service
@Slf4j
public class KafkaStreamServiceImpl implements KafkaStreamService {

    private static final int TIME_OUT = 1000;
    private static final String UNSUPPORTED_MESSAGE = "The message is not supported.";

    @Value(value = "${kafka.consumer.messagekey}")
    private String inboundMessageKey;

    @Value(value = "${kafka.publish.key}")
    private String publishMsgKey;

    @Value(value = "${kafka.publish.topic}")
    private String publishTopic;

    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private X12ToFhirService x12MapService;

    private synchronized ConsumerRecords<String, String> pollKafkaMessage(
            List<String> topics, boolean isCommitting) throws StreamException {
        try {
            log.info("pollKafkaMessage from topics: {}", topics.toString());
            consumer.subscribe(topics);
            ConsumerRecords<String, String> result = consumer.poll(Duration.ofMillis(TIME_OUT));
            if (isCommitting) {
                consumer.commitSync();
            }
            consumer.unsubscribe();  // TODO: put it in final?
            return result;
        } catch (Exception e) {
            throw new StreamException("Failed polling Kafka message for topics: {}" +
                    String.join(",", topics), e);
        }
    }

    @Override
    public void publishMessage(String topic, String key, String message)
                throws StreamException {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
            template.send(record).get(10, TimeUnit.SECONDS);
            log.info("post a message of {} bytes to topic: {} for key: {}",
                    message.length(), topic, key);
        } catch (Exception e) {
            log.error("Post kafka message to topic {} failed.", topic, e);
            throw new StreamException("failed to post to topic " + topic, e);
        }
    }

    @Override
    public List<EdiFhirMessage> pollMessage(String topic, boolean isCommitting) throws StreamException {
        try {
            ConsumerRecords<String, String> records =
                    pollKafkaMessage(Collections.singletonList(topic), isCommitting);
            List<EdiFhirMessage> result = new ArrayList<>();
            records.forEach(r -> result.add(
                    new EdiFhirMessage(topic, Optional.ofNullable(r.key()).orElse(""), r.value())));
            log.info("{} messags pulled from {}", result.size(), topic);
            return result;
        } catch (StreamException e) {
            String errMsg = String.format("Failed to poll Kafka message for topic, %s.", topic);
            log.error(errMsg, e);
            throw new StreamException(errMsg, e);
        }
    }

    @Override
    public String processMessage(ConsumerRecord<String, String> record) {
            log.info("Messge topic: {}, key: {} \n{}",
                    record.topic(), record.key(), record.value());
            if (inboundMessageKey.equals(record.key())) {
                try {
                    List<Fhir837> result = x12MapService.get837FhirBundles(record.value());
                    log.info("Convert the 837 message to {} Fhir bundles.", result.size());
                    Integer cnt = result.stream().map(r -> {
                        try {
                            publishMessage(publishTopic, publishMsgKey, r.toJson());
                            return 1;
                        } catch (StreamException e) {
                            log.error("failed to post {}", r.toJson());
                            return 0;
                        }
                    }).mapToInt(Integer::intValue).sum();
                    String returnMsg = String.format("Published %d Fhir bundles to %s", cnt, publishMsgKey);
                    log.info(returnMsg);
                    return returnMsg;
                } catch (X12ToFhirException | InvalidDataException e) {
                    String errMsg = String.format("x12 837 mapping error. %s", e.getMessage());
                    log.error(errMsg, e);
                    return errMsg;
                }
            } else {
                return UNSUPPORTED_MESSAGE;
            }
    }


}
