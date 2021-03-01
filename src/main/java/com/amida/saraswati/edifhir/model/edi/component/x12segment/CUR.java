package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object represents CUR segment in X12.
 *
 * @author Warren Lin
 */

@Data
@NoArgsConstructor
public class CUR {
    private String cur01;
    private String cur02;
    private String cur03;
    private String cur04;
    private String cur05;
    private String cur06;
    private String cur07;
    private String cur08;
    private String cur09;
    private String cur10;
    private String cur11;
    private String cur12;
    private String cur13;
    private String cur14;
    private String cur15;
    private String cur16;
    private String cur17;
    private String cur18;
    private String cur19;
    private String cur20;
    private String cur21;

    public CUR(String idCode, String code) {
        this.cur01 = idCode;
        this.cur02 = code;
    }
}
