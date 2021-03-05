package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.exception.X12ParseException;
import com.amida.saraswati.edifhir.model.edi.component.HasDate;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.DTP;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * It represents DTP segment in x12/837.
 *
 * @author warren
 */

@Slf4j
public class DTP837 extends DTP implements HasX12ParserSegment, HasDate {

    public DTP837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getDateTimeQualifier() {
        return getDtp01();
    }

    public Date getDate() {
        if (hasValidCode()) {
            try {
                return getX12Date(getDtp02(), getDtp03());
            } catch (ParseException | X12ParseException e) {
                return null;
            }
        }
        return null;
    }

    public boolean hasValidCode() {
        return !StringUtils.isEmpty(getDtp02()) &&
                !StringUtils.isEmpty(getDtp03());
    }
}
