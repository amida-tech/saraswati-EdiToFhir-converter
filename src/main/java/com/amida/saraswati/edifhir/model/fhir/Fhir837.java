package com.amida.saraswati.edifhir.model.fhir;

import ca.uhn.fhir.model.api.annotation.ResourceDef;

import java.util.Date;

/**
 * A FHIR bundle is used to map an EDI x12-837 transaction.
 *
 * @author Warren Lin
 */

@ResourceDef(name="Bundle", profile="http://hl7.org/fhir/StructureDefinition/Bundle")
public class Fhir837 extends X12FhirBundle {
    private static final String ID = "X12-837";

    public Fhir837() {
        super();
        setId(ID);
        setTimestamp(new Date());
    }

    public String toJson() {
        return serializeToJson(this);
    }
}
