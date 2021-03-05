package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.PAT;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a PAT segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
public class PAT837 extends PAT implements HasX12ParserSegment {

    public PAT837(Segment seg) {
        super();
        copy(this, seg);
    }
}
