package com.amida.saraswati.edifhir.service.stream.impl;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.model.streammessage.EdiFhirMessage;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * It implements the service for kafka stream.
 *
 * @author warren lin
 */
@Service
@Slf4j
public class KafkaStreamServiceImpl implements KafkaStreamService {

    @Data
    @AllArgsConstructor
    private static class FhirMessage {
        private Date timestamp;
        private ConsumerRecord<String, String> message;
    }

    private static final String UNSUPPORTED_MESSAGE = "The message is not supported.";
    private static final int FHIR_MESSAGE_LIMIT = 5;

    @Value(value = "${kafka.consumer.messagekey}")
    private String inboundMessageKey;

    @Value(value = "${kafka.publish.key}")
    private String publishMsgKey;

    @Value(value = "${kafka.publish.topic}")
    private String publishTopic;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private X12ToFhirService x12MapService;

    private final Queue<FhirMessage> fhirMessageQueue = new LinkedList<>();
    private int fhirMessageCount = 0;

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
    public List<EdiFhirMessage> pollMessage() {
            List<EdiFhirMessage> result = new ArrayList<>();
            fhirMessageQueue.forEach(r -> result.add(
                    new EdiFhirMessage(r.getTimestamp(), r.getMessage().topic(),
                            r.getMessage().key(), r.getMessage().value())));
            return result;
    }

    @Override
    public void process837Message(ConsumerRecord<String, String> record) {
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
                    String returnMsg = String.format("Published %d Fhir bundles to %s with key %s",
                            cnt, publishTopic, publishMsgKey);
                    log.info(returnMsg);
                } catch (X12ToFhirException | InvalidDataException e) {
                    String errMsg = String.format("x12 837 mapping error. %s", e.getMessage());
                    log.error(errMsg, e);
                }
            } else {
                log.info("{} key: {}, topic: {}", UNSUPPORTED_MESSAGE,
                        record.key(), record.topic());
            }
    }

    @Override
    public void process835Message(ConsumerRecord<String, String> record) {
        // TODO: to be implemented
    }

    @Override
    public void process834Message(ConsumerRecord<String, String> record) {
        // TODO: to be implemented.
    }

    @Override
    public synchronized void countFhirMessage(ConsumerRecord<String, String> record) {
        if (fhirMessageQueue.size() == FHIR_MESSAGE_LIMIT) {
            fhirMessageQueue.remove();
        }
        boolean success = fhirMessageQueue.offer(
                new FhirMessage(new Date(), record));
        if (success) {
            log.info("A {} message from {} topic has been pushed. message queue size: {}.",
                    record.topic(), record.key(), fhirMessageQueue.size());
            fhirMessageCount++;
            log.info("Total message count: {}", fhirMessageCount);
        } else {
            log.info("Push {} message from {} topic failed. message queue size: {}.",
                    record.topic(), record.key(), fhirMessageQueue.size());
        }
    }


}
