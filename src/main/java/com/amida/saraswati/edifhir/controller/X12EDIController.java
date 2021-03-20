package com.amida.saraswati.edifhir.controller;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.model.x12passer.X12LoopInfo;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.util.X12ParserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private X12ToFhirService service;

    @PostMapping("/x12loop")
    public ResponseEntity<String> getX12Loops(
            @RequestBody String x12Data,
            @RequestParam(required = false, name = "showSegment") boolean showSegment,
            @RequestParam(required = false, name = "showFhirMappingInfo") boolean showFhirMappingInfo,
            @RequestParam(required = false, name = "x12DataType") String x12DataType) {
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
            String result;
            if (showFhirMappingInfo) {
                x12DataType = x12DataType == null ? "837" : x12DataType;
                List<X12LoopInfo> info =
                        X12ParserUtil.loopTraviseWithInfo(reader.getLoops(), X12DATA_TYPE.getType(x12DataType));
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);
            } else {
                result = X12ParserUtil.loopTravise(reader.getLoops(), 1,
                        Optional.of(showSegment).orElse(false));
            }
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

    @GetMapping("/info")
    public ResponseEntity<String> healthy() {
        String resp =
                "endpoints: \n" +
                        "/edi/x12loop: list EDI data loop structure. " + "\n" +
                        "              parameter: showSegment = true/false, It is optional, default to false." + "\n" +
                        "              parameter: x12DataType = 837. It is optional, default to 837." + "\n" +
                        "              body: 837 transaction text." +
                        "\n" +
                        "/edi/x12ToFhir: convert EDI 837 to a list of FHIR bundles\n" +
                        "              parameter: x12DataType = 837. It is optional, default to 837." + "\n" +
                        "              body: 837 transaction text." +
                        "\n" +
                        "/edi/poststream: post an EDI 837 transaction to a kafka topic\n" +
                        "              parameter: topic, e.g., ?topic=Edi837." + "\n" +
                        "              body: 837 transaction text." +
                        "\n" +
                        "/edi/getstreammessage: get a list of recent messages in a kafka outbound (Fhir837) stream.\n" +
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
