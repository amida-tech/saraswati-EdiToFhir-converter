/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.data.edi.component.segment */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NM1 {

    private String nm101;  // entityIdCode
    private String nm102;  // entityTypeQualifier
    private String nm103;  // lastName
    private String nm104;  // fistName
    private String nm105;  // middleName
    private String nm106;  // prefix
    private String nm107;  // suffix
    private String nm108;  // IdCodeQualifier
    private String nm109;  // IdCode
    private String nm110;
    private String nm111;
    private String nm112;

}
