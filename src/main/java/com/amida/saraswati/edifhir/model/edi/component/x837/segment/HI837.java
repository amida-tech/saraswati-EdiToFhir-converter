package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.HI;
import com.imsweb.x12.Segment;

/**
 * It represents HI segment for 837.
 *
 * @author warren
 */
public class HI837 extends HI implements HasX12ParserSegment {

    public HI837(Segment seg) {
        super();
        copy(this, seg);
    }
}
