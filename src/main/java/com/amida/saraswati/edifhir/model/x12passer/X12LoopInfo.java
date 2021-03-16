package com.amida.saraswati.edifhir.model.x12passer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Warren Lin
 */
@Data
@NoArgsConstructor
public class X12LoopInfo {
    private String loopName;
    private String description;
    private String fhirMappingInfo;
    private String segmanetNames;

    private List<X12LoopInfo> subloops;

    @JsonIgnore
    private List<X12SegmentInfo> segments;
}
