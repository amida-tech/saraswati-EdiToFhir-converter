package com.amida.saraswati.edifhir.model;

import org.hl7.fhir.r4.model.Bundle;

public interface HasEdi {
    Bundle toFhirBundle();
    String toEdiText();
}
