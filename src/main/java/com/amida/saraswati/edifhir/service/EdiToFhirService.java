package com.amida.saraswati.edifhir.service;

/**
 * The interface contains to services for converting an X12Transaction
 * object to a FHIR bundle.
 */
import com.amida.saraswati.edifhir.exception.EdiToFhirException;
import com.amida.saraswati.edifhir.model.edi.X12Transaction;
import org.hl7.fhir.r4.model.Bundle;

public interface EdiToFhirService {
    Bundle getFhirBundle(X12Transaction edi) throws EdiToFhirException;
}
