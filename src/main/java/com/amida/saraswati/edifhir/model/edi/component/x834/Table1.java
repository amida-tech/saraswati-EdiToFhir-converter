package com.amida.saraswati.edifhir.model.edi.component.x834;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import com.amida.saraswati.edifhir.model.edi.component.x834.segment.DTP834;

import java.util.List;

/**
 * X12 Transaction Set Heading Area.
 */
public class Table1 {
    private ST header;
    private BGN beginSeg;
    private REF tsPolicyNo;
    private List<DTP834> fileEfectiveDate;
    private List<QTY> tsControlTotals;  // Limit to 3. Optional.
    private Loop1000A sponserName;
    private Loop1000A payer;
    private Loop1000C tbaBrockerName;
}
