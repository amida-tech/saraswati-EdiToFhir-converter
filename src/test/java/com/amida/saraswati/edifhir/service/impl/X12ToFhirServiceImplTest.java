package com.amida.saraswati.edifhir.service.impl;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.mapper.X837Mapper;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for X12ToFhirServiceImpl.
 *
 * @author Warren Lin
 */

@Slf4j
@ContextConfiguration(classes = {X837Mapper.class, X12ToFhirServiceImpl.class})
@ExtendWith(SpringExtension.class)
class X12ToFhirServiceImplTest {

    private static final String SIMPLE_TEST_837_FILE = "x12-837-example-1.txt";
    private static final String MULTI_ST_TEST_837_FILE = "CHPW_Claimdata.txt";
    private static final String PROBLEM_TEST_837_FILE = "CHPW_Claimdata2.txt";

    @Autowired
    private X12ToFhirService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void get834FhirBundle() {
    }

    @Test
    void get835FhirBundle() {
    }

    @Test
    void get837FhirBundle() {
        try {
            Path testfile = Paths.get("src", "test", "resources", SIMPLE_TEST_837_FILE);
            List<Fhir837> result = service.get837FhirBundles(testfile.toFile());
            assertNotNull(result);
            assertEquals(1, result.size());

            testfile = Paths.get("src", "test", "resources", MULTI_ST_TEST_837_FILE);
            X12Reader reader = new X12Reader(X12Reader.FileType.ANSI837_5010_X222,
                    new FileInputStream(testfile.toFile()));
            result = service.get837FhirBundles(reader);
            assertEquals(5, result.size());

            testfile = Paths.get("src", "test", "resources", PROBLEM_TEST_837_FILE);
            reader = new X12Reader(X12Reader.FileType.ANSI837_5010_X222,
                    new FileInputStream(testfile.toFile()));
            assertTrue(reader.getErrors().size() > 0);
        } catch (X12ToFhirException | InvalidDataException | FileNotFoundException e) {
            log.error("Test get837FhirBundle failed.", e);
            fail("get837FhirBundle");
        } catch (IOException e) {
            log.error("Test get837FhirBundle failed. Failed to get X12Reader.", e);
            fail("get837FhirBundle");
        }
    }
}