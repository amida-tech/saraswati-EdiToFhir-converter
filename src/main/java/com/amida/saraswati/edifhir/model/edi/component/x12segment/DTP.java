package com.amida.saraswati.edifhir.model.edi.component.x12segment;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object represents DTP segment in X12.
 *
 * @author Warren Lin
 */

@Data
@NoArgsConstructor
public class DTP {

    private String dtp01;
    private String dtp02;
    private String dtp03;
}
