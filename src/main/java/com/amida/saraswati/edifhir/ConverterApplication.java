package com.amida.saraswati.edifhir;

import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
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

	@EventListener(ApplicationReadyEvent.class)
	public void startKafkaListeningAfterStartup() {
		if (hasKafka) {
			try {
				service.listenToTopic(Arrays.asList(subscribeTopic));
				log.info("Started kafka listennig service for topics, {}.", subscribeTopic);
			} catch (StreamException e) {
				log.error("kafka stream listenning server failed.", e);
			}
		} else {
			System.out.println("No kafka stream service is set.");
		}
	}
}
