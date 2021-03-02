package com.amida.saraswati.edifhir.controller;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.util.X12ParserUtil;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The RESTful controller contains the endpoints for EDI X12 transactions to
 * FHIR bundles mapping.
 *
 * @author Warren Lin
 */

@RestController
@RequestMapping(value = "/edi/v1")
@Slf4j
public class X12EDIController {

    @Autowired
    private X12ToFhirService service;

    @GetMapping("/x12loop")
    public ResponseEntity<String> getX12Loops(
            @RequestBody String x12Data,
            @RequestHeader boolean showSegment)
    {
        try {
            log.info("x12loop showSegment: {}", showSegment);
            X12Reader reader = new X12Reader(X12Reader.FileType.ANSI837_5010_X222,
                    new ByteArrayInputStream(x12Data.getBytes()));
            if (!reader.getErrors().isEmpty()) {
                reader.getErrors().forEach(log::error);
                return ResponseEntity.badRequest().body(reader.getErrors().get(0));
            }
            String result = X12ParserUtil.loopTravise(reader.getLoops(), 1, showSegment);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Unsupported EDI X12-837 data.");
        }
    }

    @GetMapping("/x12ToFhir")
    public ResponseEntity<String> getEdi837ToFhir(@RequestBody String x12Data) {
        log.info("x12ToFhir");
        try {
            X12Reader reader = new X12Reader(X12Reader.FileType.ANSI837_5010_X222,
                    new ByteArrayInputStream(x12Data.getBytes()));
            if (!reader.getErrors().isEmpty()) {
                log.error("Invalid EDI X12 837 data {}. {}",
                        reader.getErrors().size(), reader.getErrors().get(0));
                reader.getErrors().forEach(log::error);
                return ResponseEntity.badRequest().body(reader.getErrors().get(0));
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
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
