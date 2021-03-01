package com.amida.saraswati.edifhir.model.edi;

import com.amida.saraswati.edifhir.model.HasEdi;
import com.amida.saraswati.edifhir.model.edi.component.x834.Table1;
import com.amida.saraswati.edifhir.model.edi.component.x834.Table2;
import org.hl7.fhir.r4.model.Bundle;

public class Edi834 extends X12Transaction implements HasEdi {

    private static final String ID = "834";

    private Table1 table1;
    private Table2 table2;

    public Edi834() {
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
