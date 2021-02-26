package com.amida.saraswati.edifhir.model.edi.component.segment;

public class ST {
    private static final String TAG = "ST";

    private String tsID;  // Transaction Set ID. Required
    private String tsControlNumber;  // Transaction Set Control Number. Required
    private String implConveRef;  // Implementaion Convention Reference. Required

    public ST(String id) {
        tsID = id;
    }

    public ST(String id, String controlNo, String ref) {
        this.tsID = id;
        this.tsControlNumber = controlNo;
        this.implConveRef = ref;
    }
}
