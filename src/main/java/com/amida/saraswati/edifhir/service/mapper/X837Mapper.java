/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.service.mapper */
package com.amida.saraswati.edifhir.service.mapper;

import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Maps the X12Reader to a fhir bundle.
 */
@Slf4j
@Component
public class X837Mapper {
    public Fhir837 getFhirBundle(X12Reader reader) throws X12ToFhirException {
        Fhir837 result = new Fhir837();

        // TODO: to be finished.
        return result;
    }
}
