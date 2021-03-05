package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PER (Communication Contact) segment in X12.
 *
 * @author Warren Lin
 */
@Data
@NoArgsConstructor
public class PER {
    private String per01;  // contactFunctionCode;
    private String per02;  // name
    private String per03;  //numberQualifier;
    private String per04;  // e.g., firstName;
    private String per05;  // e.g., middleName;
    private String per06;  // e.g., prefix;
    private String per07;  // e.g., suffix;
    private String per08;  //IdCodeQualifier;
    private String per09;  //idCode;
    private String per10;  //entityRelatedCode; // not used.
    private String per11;  //relatedIdCode; // not used.
    private String per12;  //orgName; // not used.

}
