package com.amida.saraswati.edifhir.service;

import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir834;
import com.amida.saraswati.edifhir.model.fhir.Fhir835;

import java.io.File;

/**
 * The interface defines the services for using x12-parser to parse a
 * given x12 EDI transaction file and create a Fhir bundle.
 */
public interface X12ToFhirService {

    /**
     * Converts the given x12-834 transaction file to a FHIR bundle, Fhir834.
     *
     * @param x834file 834 transaction file.
     * @return a Fhir834 object.
     * @throws X12ToFhirException error occurs.
     */
    Fhir834 get834FhirBundle(File x834file) throws X12ToFhirException;

    /**
     * Converts the given x12-835 transaction file to a FHIR bundle, Fhir835.
     *
     * @param x835file
     * @return a Fhir835 object.
     * @throws X12ToFhirException
     */
    Fhir835 get835FhirBundle(File x835file) throws X12ToFhirException;

    /**
     * Converts the given x12-837 transaction file to a FHIR bundle, Fhir837.
     *
     * @param x837file
     * @return a Fhir837 object.
     * @throws X12ToFhirException
     */
    Fhir834 get837FhirBundle(File x837file) throws X12ToFhirException;
}
