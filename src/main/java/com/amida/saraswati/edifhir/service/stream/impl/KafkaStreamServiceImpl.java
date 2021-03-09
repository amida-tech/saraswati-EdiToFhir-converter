package com.amida.saraswati.edifhir.service.stream.impl;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
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
    private static final int SLEEP_TIME = 2;

    @Value(value = "${kafka.consumer.messagekey}")
    private String inboundMessageKey;

    @Value(value = "${kafka.publish.key}")
    private String publishMsgKey;

    @Value(value = "${kafka.publish.topic}")
    private String publishTopic;

    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaProducer<String, String> producer;

    @Autowired
    private X12ToFhirService x12MapService;

    @Override
    @Async
    @SuppressWarnings("InfiniteLoopStatement")
    public void listenToTopic(List<String> topics) throws StreamException {
        try {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(TIME_OUT));
                log.info("received {} messages", records.count());
                for (ConsumerRecord<String, String> record : records) {
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
                TimeUnit.SECONDS.sleep(SLEEP_TIME);
            }
        } catch (Exception e) {
            String topicString = String.join(",", topics);
            String errorMsg = String.format("Listening to kafka stream topics, %s.", topicString);
            log.error(errorMsg, e);
            throw new StreamException(errorMsg, e);
        }
    }

    @Override
    public void publishMessage(String topic, String key, String message)
                throws StreamException {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
            producer.send(record);
            log.info("post a message of {} bytes to topic: {} for key: {}",
                    message.length(), topic, key);
        } catch (Exception e) {
            log.error("Post kafka message to topic {} failed.", topic, e);
            throw new StreamException("failed to post to topic " + topic, e);
        }
    }
}
