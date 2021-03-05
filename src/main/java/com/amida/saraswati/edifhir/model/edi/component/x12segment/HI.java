package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object represents HI segment in X12.
 *
 * @author Warren Lin
 */

@Data
@NoArgsConstructor
public class HI {
    private String hi01;
    private String hi02;
    private String hi03;
    private String hi04;
    private String hi05;
    private String hi06;
    private String hi07;
    private String hi08;
    private String hi09;
    private String hi10;
    private String hi11;
    private String hi12;
}
