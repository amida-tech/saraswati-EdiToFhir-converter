/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;

@Data
public class N1 {
    private static final String[] ENTITY_ID_CODES = {"PS"};  // plan sponsor.
    private static final String[] ID_CODE_TYPES = {"24", "94", "FI"};

    private String entityId;
    private String name;
    private String idCodeQualifier;
    private String idCode;
    private String entityRelationship; // not used.
    private String entityIdCode;  // Not used.

}
