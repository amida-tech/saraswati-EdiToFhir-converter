package com.amida.saraswati.edifhir.model.streammessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It represents a text message from Kafka stream.
 *
 * @author warren lin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdiFhirMessage {
    private String topic;
    private String key;
    private String value;
}
