/* warren created on 2/26/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837.segment */
package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.exception.X12ParseException;
import com.amida.saraswati.edifhir.model.edi.component.HasDate;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.DMG;
import com.imsweb.x12.Segment;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The object represents a demoghrapic segment in X12-837.
 *
 * @author Warren Lin
 */

@Slf4j
public class DMG837 extends DMG implements HasX12ParserSegment, HasDate {

    public DMG837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getGenderCode() {
        return getDmg03();
    }

    public Date getBirthDate() {
//        if ("D8".equalsIgnoreCase(getDmg01())) {
//            DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//            try {
//                return fmt.parse(getDmg02());
//            } catch (ParseException e) {
//                log.error("Invalid birth date for D8 specification. {}", getDmg02());
//            }
//        }
        try {
            return getX12Date(getDmg01(), getDmg02());
        } catch (ParseException | X12ParseException e) {
            return null;
        }
    }
}
