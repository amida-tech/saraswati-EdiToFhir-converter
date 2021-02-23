package com.amida.saraswati.edifhir.model;

public interface HasFhirBundle {
    Class<? extends HasFhirBundle> getType();
    HasEdi getEdi();
    String getJson();
    String getXml();
}
