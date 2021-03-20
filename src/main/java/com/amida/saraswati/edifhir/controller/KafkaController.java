package com.amida.saraswati.edifhir.controller;

import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.model.streammessage.EdiFhirMessage;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

/**
 * It handles all the endpoints for Kafkar stream service.
 *
 * @author Warren Lin
 */

@ConditionalOnProperty(name = "FEATURE_KAFKA", havingValue = "true")
@RestController
@RequestMapping(value = "/edi")
@Slf4j
public class KafkaController {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Value(value = "${kafka.consumer.topic}")
    private String consumerTopic;

    @Value(value = "${kafka.publish.topic}")
    private String publishTopic;

    @Value(value = "${kafka.publish.key}")
    private String messageKey;

    @Autowired
    private KafkaStreamService streamService;


    @PostMapping("/poststream")
    public ResponseEntity<String> postmessage(
            @RequestBody String data, @RequestParam(name = "topic") String topic
    ) {
        log.info("postmessage to {}", Optional.ofNullable(topic).orElse("Not Provided!"));
        try {
            if (consumerTopic.equals(topic) || publishTopic.equals(topic)) {
                streamService.publishMessage(topic, messageKey, data);
                return ResponseEntity.ok("message posted");
            } else {
                return ResponseEntity.badRequest().body("Unsupported topic.");
            }
        } catch (StreamException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getstreammessage")
    public ResponseEntity<String> getMessage() {
        log.info("getstreammessage");
        List<EdiFhirMessage> result = streamService.pollMessage();
        SimpleDateFormat dft = new SimpleDateFormat(DATETIME_FORMAT);
        StringBuilder msg = new StringBuilder();
        String headline = String.format("Total messages received: %d\n",
                result.size());
        msg.append(headline);
        for (EdiFhirMessage r : result) {
            msg.append("\n").append("Time: ").append(dft.format(r.getTimestamp()))
                    .append("key : ").append(r.getKey()).append("\n")
                    .append(r.getValue()).append("\n");
        }
        return ResponseEntity.ok(msg.toString());
    }
}
