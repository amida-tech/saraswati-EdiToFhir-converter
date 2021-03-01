package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x834.Loop1000A;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;

/**
 * X12 Transaction Set Transaction Control Information.
 * It is HEADER loop, Table1
 */
public class Loop1000 {
    private ST header;
    private BHT bht;  // Beginning of Hierarchical Transaction
    private Loop1000A submitter;
    private Loop1000B receiver;
}
