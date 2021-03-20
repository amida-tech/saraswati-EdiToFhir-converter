package com.amida.saraswati.edifhir.service.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Sets up Kafka listner to listen to EDI inbound stream.
 *
 * @author Warren Lin
 */

@ConditionalOnProperty(name = "FEATURE_KAFKA", havingValue = "true")
@Component
@Slf4j
public class EdiTopicListener {
    @Value(value = "${kafka.consumer.topic}")
    private String subscribeTopic;

    @Autowired
    KafkaStreamService service;

    @KafkaListener(id = "x12Listener", topics = "#{'${kafka.consumer.topic}'}",
            autoStartup = "true", concurrency = "2")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Receive message from {}, key={}", subscribeTopic, record.key());
        log.info("topic={}, offset={}, partition={}.", record.topic(), record.offset(), record.partition());
        service.process837Message(record);
        log.info("message from {} processed.", subscribeTopic);
    }
}
