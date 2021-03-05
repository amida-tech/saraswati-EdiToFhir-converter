package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.N4;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a N4 segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
public class N4837 extends N4 implements HasX12ParserSegment {

    public N4837(Segment seg) {
        super();
        copy(this, seg);
    }
}
