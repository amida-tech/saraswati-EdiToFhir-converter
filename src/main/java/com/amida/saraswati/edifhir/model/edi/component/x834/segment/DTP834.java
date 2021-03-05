package com.amida.saraswati.edifhir.model.edi.component.x834.segment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object represents DTP segment in X12-834.
 *
 * @author Warren Lin
 */

@Data
@NoArgsConstructor
public class DTP834 {
    private static final String[] DATETIME_QUALIFIERS = {
            "007", "090", "091", "303", "382", "388"
    };
    private static final String[] DATETIME_FORMAT_CODE = {"DB"}; // YYYYMMDD

    private String dtQualifier;  // specifying the type of data/time, e.g., Report Starting. Required
    private String dtPeriodQualifier;  // Code indicating data/time format. Required
    private String period;  // Date time period. Required.
}
