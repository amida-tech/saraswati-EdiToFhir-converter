/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.data.edi.component.segment */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PER {
    private String contactFunctionCode;
    private String name;
    private String numberQualifier;
    private String firstName;
    private String middleName;
    private String prefix;
    private String suffix;
    private String IdCodeQualifier;
    private String idCode;
//    private String entityRelatedCode; // not used.
//    private String relatedIdCode; // not used.
//    private String orgName; // not used.

    public PER(String code) {
        this.contactFunctionCode = code;
    }
}
