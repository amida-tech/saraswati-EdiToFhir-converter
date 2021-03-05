package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.N3;
import com.imsweb.x12.Segment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a N3 segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
@NoArgsConstructor
public class N3837 extends N3 implements HasX12ParserSegment {

    public N3837(Segment seg) {
        super();
        copy(this, seg);
    }
}
