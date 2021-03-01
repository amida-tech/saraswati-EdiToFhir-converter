package com.amida.saraswati.edifhir.service;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir834;
import com.amida.saraswati.edifhir.model.fhir.Fhir835;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.imsweb.x12.reader.X12Reader;

import java.io.File;
import java.util.List;

/**
 * The interface defines the services for using x12-parser to parse a
 * given x12 EDI transaction file and create a Fhir bundle.
 *
 * @author Warren Lin
 * */
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
     * @param x835file x12-835 transaction file.
     * @return a Fhir835 object.
     * @throws X12ToFhirException error occurs.
     */
    Fhir835 get835FhirBundle(File x835file) throws X12ToFhirException;

    /**
     * Converts the given x12-837 transaction file to a FHIR bundle, Fhir837.
     *
     * @param x837file x837 transaction file.
     * @return a list of Fhir837 object.
     * @throws X12ToFhirException error occurs
     *
     */
    List<Fhir837> get837FhirBundles(File x837file) throws X12ToFhirException, InvalidDataException;

    /**
     * Converts the given X12Reader to a Set of Fhir837.
     *
     * @param x12Reader A X12Reader containing a x12-837 .
     * @return a set of Fhir837
     * @throws X12ToFhirException
     * @throws InvalidDataException
     */
    List<Fhir837> get837FhirBundles(X12Reader x12Reader) throws X12ToFhirException, InvalidDataException;

}
