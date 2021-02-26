/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.service.mapper */
package com.amida.saraswati.edifhir.service.mapper;

import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir834;
import com.imsweb.x12.reader.X12Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mappers an EDI 834 data to FHIR bundle.
 */
@Component
@Slf4j
public class X834Mapper {

    public Fhir834 getFhirBundle(X12Reader reader) throws X12ToFhirException {
        Fhir834 result = new Fhir834();

        // TODO: to be finished.
        return result;
    }
}
