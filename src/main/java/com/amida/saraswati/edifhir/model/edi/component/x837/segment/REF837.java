package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.REF;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a REF segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
public class REF837 extends REF implements HasX12ParserSegment {
    private static final String CLAIM_NO_IDENTIFIER = "D9";

    public REF837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getQualifier() {
        return getRef01();
    }

    public String getId() {
        return getRef02();
    }

    public String getClaimNumber() {
        return CLAIM_NO_IDENTIFIER.equals(getRef01()) ?
                getRef02() : null;
    }
}
