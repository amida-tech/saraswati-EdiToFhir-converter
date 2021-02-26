/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.service.impl */
package com.amida.saraswati.edifhir.service.impl;

import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir834;
import com.amida.saraswati.edifhir.model.fhir.Fhir835;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.service.X12ToFhirService;
import com.amida.saraswati.edifhir.service.mapper.X834Mapper;
import com.amida.saraswati.edifhir.service.mapper.X837Mapper;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.spi.XmlReader;
import java.io.File;
import java.io.IOException;

/**
 * Implements X12 file data to fhir bundle convertion using x12-parser.
 */
@Service
@Slf4j
public class X12ToFhirServiceImpl implements X12ToFhirService {

    @Autowired
    private X837Mapper x837Mapper;

    @Autowired
    private X834Mapper x834Mapper;

    @Override
    public Fhir834 get834FhirBundle(File x834file) throws X12ToFhirException {
        return null;
    }

    @Override
    public Fhir835 get835FhirBundle(File x835file) throws X12ToFhirException {
        return null;
    }

    @Override
    public Fhir837 get837FhirBundle(File x837file) throws X12ToFhirException {
        try {
            X12Reader x12Reader = new X12Reader(X12Reader.FileType.ANSI837_5010_X231, x837file);
            return x837Mapper.getFhirBundle(x12Reader);
        } catch (IOException e) {
            log.error("Failed to create X12Reader. for 837", e);
            throw new X12ToFhirException();
        }
    }
}
