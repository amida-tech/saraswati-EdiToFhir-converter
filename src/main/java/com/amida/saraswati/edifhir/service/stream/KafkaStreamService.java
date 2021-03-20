package com.amida.saraswati.edifhir.service.stream;

import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.model.streammessage.EdiFhirMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface KafkaStreamService {

    /**
     * Push a message to a topic in Kafka stream.
     *
     * @param topic stream topic.
     * @param key message key.
     * @param message message string.
     * @throws StreamException error occurs.
     */
    void publishMessage(String topic, String key, String message) throws StreamException;

    /**
     * Checks the FHIR messages from sent to in Kafa outbound stream after converting the
     * inbound EDI messages. It is intended to be used for test/verify the Kafka stream
     * service in this application.
     *
     */
    List<EdiFhirMessage> pollMessage();

    /**
     * Processes a Kafka message related to x12 837 transaction.
     *
     * @param record A Kafka message record for an X12 837 transaction.
     */
    void process837Message(ConsumerRecord<String, String> record);

    /**
     * Processes a Kafka message related to x12 835 transaction.
     *
     * @param record A Kafka message record for an X12 835 transaction.
     */
    void process835Message(ConsumerRecord<String, String> record);

    /**
     * Processes a Kafka message related to x12 834 transaction.
     *
     * @param record A Kafka message record for an X12 834 transaction.
     */
    void process834Message(ConsumerRecord<String, String> record);

    /**
     * Counts FHIR message for testing converted message sent to outbound stream.
     *
     * @param record a record from Kafka stream.
     */
    void countFhirMessage(ConsumerRecord<String, String> record);
}
