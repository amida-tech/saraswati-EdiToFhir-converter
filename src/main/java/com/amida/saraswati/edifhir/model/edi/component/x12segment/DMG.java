package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object represents DMG segment in X12.
 *
 * @author Warren Lin
 */

@Data
@NoArgsConstructor
public class DMG {
    private String dmg01;
    private String dmg02;
    private String dmg03;
    private String dmg04;
    private String dmg05;
    private String dmg06;
    private String dmg07;
    private String dmg08;
    private String dmg09;
    private String dmg10;
    private String dmg11;
}
