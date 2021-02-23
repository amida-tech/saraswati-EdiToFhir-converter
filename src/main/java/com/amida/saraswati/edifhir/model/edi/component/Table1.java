package com.amida.saraswati.edifhir.model.edi.component;

import com.amida.saraswati.edifhir.model.edi.component.segment.*;

import java.util.List;

/**
 * X12 Transaction Set Heading Area.
 */
public class Table1 {
    private ST header;
    private BGN beginSeg;
    private REF tsPolicyNo;
    private List<DTP> fileEfectiveDate;
    private List<QTY> tsControlTotals;  // Limit to 3. Optional.
    private Loop1000A sponserName;
    private Loop1000A payer;
    private Loop1000C tbaBrockerName;
}
