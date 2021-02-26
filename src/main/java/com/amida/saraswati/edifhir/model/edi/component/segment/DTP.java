/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.segment;

public class DTP {
    private static final String[] DATETIME_QUALIFIERS = {
            "007", "090", "091", "303", "382", "388"
    };
    private static final String[] DATETIME_FORMAT_CODE = {"DB"}; // YYYYMMDD

    private String dtQualifier;  // specifying the type of data/time, e.g., Report Starting. Required
    private String dtPeriodQualifier;  // Code indicating data/time format. Required
    private String period;  // Date time period. Required.
}
