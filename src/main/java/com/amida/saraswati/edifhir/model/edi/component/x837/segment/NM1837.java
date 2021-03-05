package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.NM1;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;

/**
 * The object represents a Name segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
public class NM1837 extends NM1 implements HasX12ParserSegment {

    public NM1837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getFirstName() {
        return getNm104();
    }

    public String getLastName() {
        return getNm103();
    }

    public String getMiddleName() {
        return getNm105();
    }

    public String getIdCode() {
        return getNm109();
    }

    public boolean isNonPersonEntity() {
        return "2".equals(getNm102().trim());
    }
}
