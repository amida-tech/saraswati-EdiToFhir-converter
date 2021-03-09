package com.amida.saraswati.edifhir.controller;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.StreamException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.model.streammessage.EdiFhirMessage;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.stream.KafkaStreamService;
import com.amida.saraswati.edifhir.util.X12ParserUtil;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The RESTful controller contains the endpoints for EDI X12 transactions to
 * FHIR bundles mapping.
 *
 * @author Warren Lin
 */

@RestController
@RequestMapping(value = "/edi")
@Slf4j
public class X12EDIController {

    public enum X12DATA_TYPE {
        EDI837("837"), EDI834("834"), EDI835("835"), UNKNOWN("");

        private final String name;
        X12DATA_TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static X12DATA_TYPE getType(String name) {
            Optional<X12DATA_TYPE> type = Arrays.stream(X12DATA_TYPE.values())
                    .filter(x -> x.getName().equals(name)).findFirst();
            return type.orElse(X12DATA_TYPE.UNKNOWN);
        }
    }

    @Value(value = "${kafka.publish.key}")
    private String messageKey;

    @Autowired
    private X12ToFhirService service;

    @Autowired
    private KafkaStreamService streamService;

    @PostMapping("/x12loop")
    public ResponseEntity<String> getX12Loops(
            @RequestBody String x12Data,
            @RequestParam(required = false, name = "showSegment") boolean showSegment,
            @RequestParam(required = false, name = "x12DataType") String x12DataType)
    {
        X12Reader.FileType x12ReadFileType = getX12ReaderFileType(x12DataType);
        if (x12ReadFileType == null) {
            return ResponseEntity.badRequest().body("Not supported x12 data type.");
        }
        try {
            log.info("x12loop showSegment: {}", showSegment);
            X12Reader reader = new X12Reader(x12ReadFileType,
                    new ByteArrayInputStream(x12Data.getBytes()));
            reader.getErrors().forEach(log::error);
            if (!reader.getFatalErrors().isEmpty()) {
                return ResponseEntity.badRequest().body(reader.getFatalErrors().get(0));
            }
            String result = X12ParserUtil.loopTravise(reader.getLoops(), 1,
                    Optional.of(showSegment).orElse(false));
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Unsupported EDI X12-837 data.");
        }
    }

    @PostMapping("/x12ToFhir")
    public ResponseEntity<String> getEdi837ToFhir(
            @RequestBody String x12Data,
            @RequestParam(required = false, name = "x12DataType") String x12DataType) {
        log.info("x12ToFhir");
        X12Reader.FileType x12ReadFileType = getX12ReaderFileType(x12DataType);
        if (x12ReadFileType == null) {
            return ResponseEntity.badRequest().body("Not supported x12 data type.");
        }
        try {
            X12Reader reader = new X12Reader(x12ReadFileType,
                    new ByteArrayInputStream(x12Data.getBytes()));
            log.error("Invalid EDI X12 837 data {}. {}",
                    reader.getErrors().size(), reader.getErrors().get(0));
            reader.getErrors().forEach(log::error);
            if (!reader.getFatalErrors().isEmpty()) {
                return ResponseEntity.badRequest().body(reader.getFatalErrors().get(0));
            }
            List<Fhir837> result = service.get837FhirBundles(reader);
            String json = "{\n \"bundles\": [ " +
                    result.stream().map(Fhir837::toJson).collect(Collectors.joining(","))
                    + " ]\n}";
            return ResponseEntity.ok(json);
        } catch (IOException | InvalidDataException e) {
            log.error("EDI x12 data parsing failed. {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (X12ToFhirException e) {
            log.error("FHIR conversion failed. {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/poststream")
    public ResponseEntity<String> postmessage(
            @RequestBody String data, @RequestParam(name = "topic") String topic
    ) {
        try {
            streamService.publishMessage(topic, messageKey, data);
            return ResponseEntity.ok("message posted");
        } catch (StreamException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getstreammessage")
    public ResponseEntity<String> getMessage(
            @RequestParam(name = "topic") String topic
    ) {
        try {
            List<EdiFhirMessage> result = streamService.pollMessage(topic);
            StringBuilder msg = new StringBuilder();
            String headline = String.format("Total messages received from the topic, %s: %d\n",
                    topic, result.size());
            msg.append(headline);
            result.forEach(r -> {
                msg.append("\n").append("key : ").append(r.getKey()).append("\n");
                msg.append(r.getValue()).append("\n");
            });
            return ResponseEntity.ok(msg.toString());
        } catch (StreamException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/healthy")
    public ResponseEntity<String> healthy() {
        String resp = "I'm good. I support the following endpoints." + "\n\n" +
                "endpoints: \n" +
                "/edi/x12loop: list EDI data loop structure. " + "\n" +
                "              parameter: showSegment = true/false, It is optional, default to false." + "\n" +
                "              parameter: x12DataType = 837. It is optional, default to 837." + "\n" +
                "              body: 837 transaction text." +
                "\n" +
                "/edi/x12ToFhir: convert EDI 837 to a list of FHIR bundles\n" +
                "              parameter: x12DataType = 837. It is optional, default to 837." + "\n" +
                "              body: 837 transaction text." +
                "\n";
        return ResponseEntity.ok(resp);
    }

    private X12Reader.FileType getX12ReaderFileType(String x12DataType) {
        String fileType = Optional.ofNullable(x12DataType)
                .orElse(X12DATA_TYPE.EDI837.getName());
        switch (X12DATA_TYPE.getType(fileType)) {
            case EDI837:
                return X12Reader.FileType.ANSI837_5010_X222;
            case EDI834:
            case EDI835:
            default:
                return null;
        }
    }
}
