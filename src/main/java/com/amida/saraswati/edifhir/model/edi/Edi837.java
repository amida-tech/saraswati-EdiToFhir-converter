package com.amida.saraswati.edifhir.model.edi;

import com.amida.saraswati.edifhir.model.HasEdi;
import org.hl7.fhir.r4.model.Bundle;

public class Edi837 extends X12Transaction implements HasEdi {
    private static final String ID = "837";

    public Edi837() {
        super(ID);
    }

    @Override
    public Bundle toFhirBundle() {
        return null;
    }

    @Override
    public String toEdiText() {
        return null;
    }
}
