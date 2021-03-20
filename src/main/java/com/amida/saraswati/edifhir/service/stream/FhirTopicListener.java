package com.amida.saraswati.edifhir.service.stream;

import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Configures a kafka listener to listen Fhir topic for testing.
 *
 * @author Warren Lin
 */

@ConditionalOnProperty(name = "FEATURE_FHIR_TOPIC_LISTENER", havingValue = "true")
@Component
@Slf4j
public class FhirTopicListener {

    @Value(value = "${kafka.publish.topic}")
    private String publishTopic;

    @Autowired
    KafkaStreamService service;

    @KafkaListener(id = "fhirListener", topics = "#{'${kafka.publish.topic}'}",
				  autoStartup = "true", concurrency = "1")
	public void listenOutbound(ConsumerRecord<String, String> record) {
		log.info("Receive message from {}, key={}", publishTopic, record.key());
		log.info("topic={}, offset={}, partition={}.", record.topic(), record.offset(), record.partition());
		service.countFhirMessage(record);
	}
}
