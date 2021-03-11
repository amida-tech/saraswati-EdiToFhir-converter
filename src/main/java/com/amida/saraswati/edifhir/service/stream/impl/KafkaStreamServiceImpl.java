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
import org.springframework.kafka.annotation.KafkaListener;
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

    private synchronized ConsumerRecords<String, String> pollKafkaMessage(List<String> topics)
            throws StreamException {
        try {
            consumer.subscribe(topics);
            return consumer.poll(Duration.ofMillis(TIME_OUT));
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
    public List<EdiFhirMessage> pollMessage(String topic) throws StreamException {
        try {
            ConsumerRecords<String, String> records = pollKafkaMessage(Collections.singletonList(topic));
            List<EdiFhirMessage> result = new ArrayList<>();
            records.forEach(r -> result.add(
                    new EdiFhirMessage(topic, Optional.ofNullable(r.key()).orElse(""), r.value())));
            return result;
        } catch (StreamException e) {
            String errMsg = String.format("Failed to poll Kafka message for topic, %s.", topic);
            log.error(errMsg, e);
            throw new StreamException(errMsg, e);
        }
    }

    @Override
    public void processMessage(ConsumerRecord<String, String> record) {

            log.info("Messge topic: {}, key: {} \n{}",
                    record.topic(), record.key(), record.value());
            if (inboundMessageKey.equals(record.key())) {
                try {
                    List<Fhir837> result = x12MapService.get837FhirBundles(record.value());
                    result.forEach(r -> {
                        try {
                            publishMessage(publishTopic, publishMsgKey, r.toJson());
                        } catch (StreamException e) {
                            log.error("failed to post {}", r.toJson());
                        }
                    });
                } catch (X12ToFhirException | InvalidDataException e) {
                    log.error("x12 837 mapping error. {}", e.getMessage(), e);
                }
            }
    }

    @KafkaListener(id = "x12Listener", topics = "Edi837",
            autoStartup = "true", concurrency = "3")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Receive message from Edi837, key={}", record.key());
        processMessage(record);
    }
}
