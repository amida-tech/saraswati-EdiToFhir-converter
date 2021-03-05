package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.PWK;
import com.imsweb.x12.Segment;

/**
 * @author warren
 */
public class PWK837 extends PWK implements HasX12ParserSegment {
    public PWK837(Segment seg) {
        super();
        copy(this, seg);
    }
}
