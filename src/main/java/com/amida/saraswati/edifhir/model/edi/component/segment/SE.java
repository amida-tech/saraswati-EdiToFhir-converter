/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.segment;

public class SE {
    private String tsID;  // Transaction Set ID. Required
    private String tsControlNumber;  // Transaction Set Control Number. Required
    private String implConveRef;  // Implementaion Convention Reference. Required

    public SE(String id) {
        tsID = id;
    }

    public SE(String id, String controlNo, String ref) {
        this.tsID = id;
        this.tsControlNumber = controlNo;
        this.implConveRef = ref;
    }
}
