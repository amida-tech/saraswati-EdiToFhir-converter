/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.segment */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;

@Data
public class HL {
    private String hl01;  // requried
    private String hl02;
    private String hl03;  // requried
    private String hl04;

    public HL(String h1, String h2, String h3, String h4) {
        this.hl01 = h1;
        this.hl02 = h2;
        this.hl03 = h3;
        this.hl04 = h4;
    }
}
