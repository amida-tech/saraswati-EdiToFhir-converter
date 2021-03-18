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
     * Poll messages from a topic in Kafa stream. It is intended to be used for test/verify
     * the Kafka stream service in this application.
     *
     * @param topic stream topic.
     * @param isCommitting indicates whether to commit after polling the message.
     * @return a list of {@link EdiFhirMessage}.
     * @throws StreamException error occurs.
     */
    List<EdiFhirMessage> pollMessage(String topic, boolean isCommitting) throws StreamException;

    /**
     * Processes a Kafka message.
     *
     * @param record A Kafka message record.
     * @return a message about the processing.
     */
    String processMessage(ConsumerRecord<String, String> record);
}
