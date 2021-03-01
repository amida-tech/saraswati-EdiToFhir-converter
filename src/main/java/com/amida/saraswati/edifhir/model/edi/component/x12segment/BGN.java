/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import java.util.Date;

/**
 * Beginning Segment
 */
public class BGN {
    private static final String[] PURPOSE_CODES = {"00", "15", "22"};
    private static final String[] TIME_CODES = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
    "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "24",
    "AD", "AS"}; // TODO: to be finished.
    private static final String[] ACTION_CODES = {"2", "4", "RX"};

    private String txPurposeCode;
    private String referenceIdentification;  // Sender sets it. Max length = 127
    private Date date;  // CCYYMMDD
    private Date time;  // 24-hour clock time, HHMM or HHMMSS
    private String timeCode;
    private String xReferenceIdent;  // referenece to another transaction if needed.
    private String transactionTypeCode;  // Not used.
    private String actionCode;
    private String securityLevelCode;  // Not used.
}
