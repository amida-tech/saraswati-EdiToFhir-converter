package com.amida.saraswati.edifhir.model.x12passer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Warren Lin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class X12SegmentInfo {
    private String segmentName;
    private String description;
    private String fhirMappingInfo;

    public X12SegmentInfo(String name) {
        segmentName = name;
    }
}
