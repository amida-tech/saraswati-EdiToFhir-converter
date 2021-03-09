package com.amida.saraswati.edifhir.service.stream;

import com.amida.saraswati.edifhir.exception.StreamException;

import java.util.List;

public interface KafkaStreamService {

    void listenToTopic(List<String> topics) throws StreamException;

    void publishMessage(String topic, String key, String message) throws StreamException;
}
