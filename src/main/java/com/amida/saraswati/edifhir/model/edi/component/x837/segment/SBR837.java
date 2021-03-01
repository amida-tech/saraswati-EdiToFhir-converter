package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.SBR;
import com.imsweb.x12.Segment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a subscriber segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
@NoArgsConstructor
public class SBR837 extends SBR implements HasX12ParserSegment {

    public SBR837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getPayerResponsibility() {
        return getSbr01();
    }

    public String getPolicyGroupNumber() {
        return getSbr03();
    }

    public String getPlanName() {
        return getSbr04();
    }

    public String getRelationShip() {
        return getSbr02();
    }

    public String getClaimFilingId() {
        return getSbr09();
    }
}
