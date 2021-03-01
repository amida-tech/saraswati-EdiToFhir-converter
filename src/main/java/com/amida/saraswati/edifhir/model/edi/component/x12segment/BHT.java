package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The object represents BHT segment in X12.
 *
 * @author Warren Lin
 */

@Data
@AllArgsConstructor
public class BHT {
    private String bh01;  // required
    private String bh02;  // required
    private String bh03;
    private String bh04;
    private String bh05;
    private String bh06;
}
