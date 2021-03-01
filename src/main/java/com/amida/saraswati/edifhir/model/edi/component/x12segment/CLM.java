package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;

/**
 * The object represents CLM segment in X12.
 *
 * @author Warren Lin
 */

@Data
public class CLM {
    private String clm01;
    private String clm02;
    private String clm03;
    private String clm04;
    private String clm05;
    private String clm06;
    private String clm07;
    private String clm08;
    private String clm09;

}
