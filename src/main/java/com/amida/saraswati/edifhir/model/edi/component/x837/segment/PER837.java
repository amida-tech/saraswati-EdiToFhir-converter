package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.PER;
import com.imsweb.x12.Segment;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * It represents an X12-837 PER segment.
 *
 * @author Warren Lin
 */
@Setter
@Slf4j
public class PER837 extends PER implements HasX12ParserSegment {
    private static final String CONTACT_FUNC_CODE = "IC";

    private static final String[] COMMUNICATION_NUMBER_TYPE = {
            "EM", // email
            "FX", // fax
            "TE",  // phone
            "EX"   // telepone number extension.
    };

    private static final String[] FHIR_CONTACT_TYPE = {
            "email", "fax", "phone", "other"
    };

    public PER837() {
        super();
        setPer01(CONTACT_FUNC_CODE);
    }

    public PER837(Segment seg) {
        super();
        copy(this, seg);
    }

    public String getName() {
        return getPer02();
    }

    public String getContactNumber1() {
        String type = getPer03();
        if (isInvalidContactNumberTypeType(type)
                | type.equals(COMMUNICATION_NUMBER_TYPE[3])) {
            log.error("Invalid contact number type in per03 {}", type);
        }
        if (getContactNumer2Type().equals(COMMUNICATION_NUMBER_TYPE[3])) {
            return getPer04() + "x" + getPer06();
        }
        return getPer04();
    }

    public String getContactNumer1Type() {
        return getPer03();
    }

    public String getContactNumber2() {
        String type = getPer05();
        if (type == null || type.equals(COMMUNICATION_NUMBER_TYPE[3])) {
            return null;
        }
        if (isInvalidContactNumberTypeType(type)) {
            log.error("Invalid contact number type in per05, {}", type);
            return null;
        }

        type = getContactNumer3Type();
        if (type != null && type.equals(COMMUNICATION_NUMBER_TYPE[3])) {
            return getPer06() + "x" + getPer08();
        }
        return getPer06();
    }

    public String getContactNumer2Type() {
        return getPer05();
    }

    public String getContactNumber3() {
        String type = getPer07();
        if ( type == null || type.equals(COMMUNICATION_NUMBER_TYPE[3])) {
            return null;
        }
        if (isInvalidContactNumberTypeType(type)) {
            log.error("Invalid contact number type in per07, {}", type);
            return null;
        }

        return getPer08();
    }

    public String getContactNumer3Type() {
        return getPer07();
    }

    public String getFhirContactPointCode1() {
        return getFhirContactPointCode(getPer03());
    }

    public String getFhirContactPointCode2() {
        return getFhirContactPointCode(getPer05());
    }

    public String getFhirContactPointCode3() {
        return getFhirContactPointCode(getPer07());
    }

    private static String getFhirContactPointCode(String type) {
        switch (type) {
            case "EM":
                return FHIR_CONTACT_TYPE[0];
            case "FX":
                return FHIR_CONTACT_TYPE[1];
            case "TE":
                return FHIR_CONTACT_TYPE[2];
            default:
                return FHIR_CONTACT_TYPE[3];
        }
    }

    private boolean isInvalidContactNumberTypeType(String type) {
        if (StringUtils.isEmpty(type)) {
            return true;
        }
        return !Arrays.asList(COMMUNICATION_NUMBER_TYPE).contains(type);
    }
}
