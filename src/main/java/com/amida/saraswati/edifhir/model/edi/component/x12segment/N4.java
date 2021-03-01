/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.segment */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class N4 {
    private String n1;  // city
    private String n2;  // state
    private String n3;  // zip
    private String n4;  // country
    private String n5;  // location qualifier
    private String n6;  // location id
    private String n7;  // country sub code
    private String n8;  // formmated postal code.

    public N4(String city, String state, String zip) {
        this.n1 = city;
        this.n2 = state;
        this.n3 = zip;
    }
}
