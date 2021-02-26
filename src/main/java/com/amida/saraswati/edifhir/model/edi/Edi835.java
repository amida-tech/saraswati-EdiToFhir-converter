package com.amida.saraswati.edifhir.model.edi;

import com.amida.saraswati.edifhir.model.HasEdi;
import org.hl7.fhir.r4.model.Bundle;

public class Edi835 extends X12Transaction implements HasEdi {

    private static final String ID = "835";

    public Edi835() {
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
