package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.SV1;
import com.imsweb.x12.Segment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a SV1 segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
@NoArgsConstructor
public class SV1837 extends SV1 implements HasX12ParserSegment {

    public SV1837(Segment seg) {
        super();
        copy(this, seg);
    }
}
