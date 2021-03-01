package com.amida.saraswati.edifhir.util;

import com.amida.saraswati.edifhir.model.edi.component.x837.segment.NM1837;
import com.imsweb.x12.Loop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.*;

import java.util.Arrays;
import java.util.List;

/**
 * It contains a set of utility methods used for processing x12 data.
 *
 * @author Warren Lin
 */
@Slf4j
public class X12Util {
    private static final String DEFAULT_CURRENCY = "USD";

    /**
     * Maps an amount to a FHIR Money object. The currency is default to US$.
     *
     * @param amt a string for an amount of money.
     * @return a Money object.
     * @throws NumberFormatException the given amount string is invalid.
     */
    public static Money getMoneyObject(String amt) throws NumberFormatException {
        Money money = new Money();
        money.setCurrency(DEFAULT_CURRENCY);
        float amtValue = Float.parseFloat(amt);

        money.setValue(amtValue);
        return money;
    }

    /**
     * Maps a NM1 segment to FHIR HumanName.
     *
     * @param nm an X12 NM1 segment.
     * @return HumnanName.
     */
    public static HumanName getHumanName(NM1837 nm) {
        HumanName humanName = new HumanName();
        StringType firstName = new StringType();
        firstName.setValue(nm.getFirstName());
        StringType middleName = new StringType();
        middleName.setValue(nm.getMiddleName());
        if (nm.isNonPersonEntity()){
            humanName.setText(nm.getLastName())
                    .setId(nm.getIdCode());
        } else {
            humanName.setFamily(nm.getLastName())
                    .setGiven(Arrays.asList(firstName, middleName))
                    .setText(String.format("%s %s %s",
                            nm.getFirstName(), nm.getMiddleName(), nm.getLastName()))
                    .setId(nm.getIdCode());
        }
        return humanName;
    }

    /**
     * Maps a gender code (M, F, other) used in X12 to a FHIR AdministrativeGender.
     *
     * @param code gender code used in X12.
     * @return AdministrativeGender.
     */
    public static Enumerations.AdministrativeGender getGenderCode(String code) {
        String sexCode = code.toUpperCase().trim();
        if (sexCode.startsWith("M")) {
            return Enumerations.AdministrativeGender.MALE;
        } else if (sexCode.startsWith("F")) {
            return Enumerations.AdministrativeGender.FEMALE;
        } else {
            return Enumerations.AdministrativeGender.UNKNOWN;
        }
    }

    /**
     * Gets a FHIR CodeableConcept.
     *
     * @param code code used for ID.
     * @param value value string used for text.
     * @return a CodeableConcept.
     */
    public static CodeableConcept getCodeConcept(String code, String value) {
        CodeableConcept concept = new CodeableConcept();
        concept.setText(value).setId(code);
        return concept;
    }
}
