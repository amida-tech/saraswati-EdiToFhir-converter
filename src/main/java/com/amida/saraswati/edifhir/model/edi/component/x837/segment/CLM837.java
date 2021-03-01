package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.CLM;
import com.imsweb.x12.Segment;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a claim segment in X12-837.
 *
 * @author Warren Lin
 */

@Data
@Slf4j
public class CLM837 extends CLM implements HasX12ParserSegment {

    private String facilityCode;
    private String facilityCodeQualifier;
    private String frequencyType;

    public CLM837() {
        super();
    }

    public CLM837(Segment clm) {
        super();
        copy(this, clm);
        String locationId = getClm05();
        String[] s = locationId.split(":");
        for (int i = 0; i < s.length; i++) {
            switch (i) {
                case 0:
                    facilityCode = s[i];
                    break;
                case 1:
                    facilityCodeQualifier = s[i];
                    break;
                case 2:
                    frequencyType = s[i];
                    break;
                default:
                    log.error("Unexpected CLM segment. {}", locationId);
            }
        }
    }

    public String getSubmitterId() {
        return getClm01();
    }

    public String getClaimAmount() {
        return getClm02();
    }

    public String getLocationId() {
        return getClm05();
    }

    public String getResponseCode() {
        return getClm06();
    }

    public String getAssigmentAccptRespCode() {
        return getClm08();
    }

    public String getReleaseInfoCode() {
        return getClm09();
    }
}
