/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.segment */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class N3 {
    private String n1;  // address 1
    private String n2;  // address 2;
}
