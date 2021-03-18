package com.amida.saraswati.edifhir;

import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@EnableKafka
public class ConverterApplication {

	@Value(value = "${FEATURE_KAFKA}")
	private boolean hasKafka;

	@Value(value = "${kafka.consumer.topic}")
	private String subscribeTopic;

	@Autowired
	KafkaStreamService service;

	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);
	}

	@KafkaListener(id = "x12Listener", topics = "#{'${kafka.consumer.topic}'}",
			autoStartup = "true", concurrency = "2")
	public void listen(ConsumerRecord<String, String> record) {
		log.info("Receive message from {}, key={}", subscribeTopic, record.key());
		service.processMessage(record);
		log.info("message from {} processed.", subscribeTopic);
	}

}
