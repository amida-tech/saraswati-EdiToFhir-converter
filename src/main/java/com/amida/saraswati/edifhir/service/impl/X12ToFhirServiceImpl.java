/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.service.impl */
package com.amida.saraswati.edifhir.service.impl;

import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir834;
import com.amida.saraswati.edifhir.model.fhir.Fhir835;
import com.amida.saraswati.edifhir.service.X12ToFhirService;

import java.io.File;

public class X12ToFhirServiceImpl implements X12ToFhirService {

    @Override
    public Fhir834 get834FhirBundle(File x834file) throws X12ToFhirException {
        return null;
    }

    @Override
    public Fhir835 get835FhirBundle(File x835file) throws X12ToFhirException {
        return null;
    }

    @Override
    public Fhir834 get837FhirBundle(File x837file) throws X12ToFhirException {
        return null;
    }
}
