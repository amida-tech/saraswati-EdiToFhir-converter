package com.amida.saraswati.edifhir.util;

import com.amida.saraswati.edifhir.model.edi.component.x837.segment.DTP837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.NM1837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.PER837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.REF837;
import com.amida.saraswati.edifhir.model.x12helper.ClaimReferences;
import com.amida.saraswati.edifhir.model.x12helper.ClaimRelatedDates;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * It contains a set of utility methods used for processing x12 data, map
 * an X12 data segemnt to a FHIR component.
 *
 * @author Warren Lin
 */
@Slf4j
public class X12Util {
    private static final String DEFAULT_CURRENCY = "USD";

    /**
     * Maps an amount to a FHIR {@link Money}. The currency is default to US$.
     *
     * @param amt a string for an amount of money.
     * @return a FHIR {@link Money} object.
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
     * Maps a NM1 segment to FHIR {@link HumanName}.
     *
     * @param nm an X12 NM1 segment.
     * @return {@link HumanName}.
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
     * Gets a {@link HumanName} from an X12 PER segment.
     *
     * @param per an X12 837 PER segment.
     * @return a {@link HumanName} object.
     */
    public static HumanName getHumanName(PER837 per) {
        HumanName humanName = new HumanName();
        humanName.setText(per.getName()).setId(per.getPer01());
        return humanName;
    }

    /**
     * Gets a list of {@link ContactPoint} from the given X12 PER segment.
     *
     * @param per837 an X12 PER segment for 837.
     * @return a list of {@link ContactPoint}
     */
    public static List<ContactPoint> getContactNumbers(PER837 per837) {
        List<ContactPoint> contacts = new ArrayList<>();
        String contactNumber = per837.getContactNumber1();
        String contactType = per837.getFhirContactPointCode1();
        if (contactNumber != null) {
            contacts.add(new ContactPoint().setValue(contactNumber)
                    .setSystem(ContactPointSystem.fromCode(contactType)));
        }
        contactNumber = per837.getContactNumber2();
        if (contactNumber != null) {
            contactType = per837.getFhirContactPointCode2();
            contacts.add(new ContactPoint().setValue(contactNumber)
                    .setSystem(ContactPointSystem.fromCode(contactType)));
        }
        contactNumber = per837.getContactNumber3();
        if (contactNumber != null) {
            contactType = per837.getFhirContactPointCode3();
            contacts.add(new ContactPoint().setValue(contactNumber)
                    .setSystem(ContactPointSystem.fromCode(contactType)));
        }
        return contacts;
    }

    /**
     * Maps a gender code (M, F, other) used in X12 to a FHIR AdministrativeGender
     * in {@link Enumerations}.
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
     * Gets a FHIR {@link CodeableConcept}.
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

    /**
     * Gets a FHIR {@link Reference} object.
     *
     * @param id reference ID.
     * @param type reference type.
     * @return a {@link Reference} object.
     */
    public static Reference getReference(String id, String type) {
        Reference ref = new Reference();
        ref.setId(id);
        ref.setReference(type + "/" + id);
        ref.setType(type);
        return ref;
    }

    /**
     * Gets a patient {@link Reference}.
     *
     * @param id reference id.
     * @return a {@link Reference} object.
     */
    public static Reference getPatientReference(String id) {
        String type = "Patient";
        return getReference(id, type);
    }

    /**
     * Populates {@link ClaimRelatedDates} with the given {@link DTP837} list
     *
     * @param dtps a list of DTP segments.
     * @return a {@link ClaimRelatedDates}
     */
    public static ClaimRelatedDates getClaimRelateddDates(List<DTP837> dtps) {
        if (dtps.isEmpty()) {
            return null;
        }
        ClaimRelatedDates dates = new ClaimRelatedDates();
        dtps.forEach(d -> updateClaimRelatedDates(dates, d));
        return dates;
    }

    /**
     * Updates a FHIR {@link ClaimRelatedDates} object with the given X12 837 DTP segment.
     *
     * @param dates the ClaimRelatedDates object to be updated.
     * @param dtp an X12 837 DTP segment.
     */
    private static void updateClaimRelatedDates(ClaimRelatedDates dates, DTP837 dtp) {
        if (dtp.hasValidCode()) {
            switch (dtp.getDateTimeQualifier()) {
                case "523":
                    dates.setOriginalCreationDate(dtp.getDate());
                    break;
                case "431":
                    dates.setIllnessStartDate(dtp.getDate());
                    break;
                case "454":
                    dates.setTreatmentStartDate(dtp.getDate());
                    break;
                case "304":
                    dates.setLastSeenDate(dtp.getDate());
                    break;
                case "439":
                    dates.setAccidentDate(dtp.getDate());
                    break;
                case "484":
                    dates.setLastMensPeriodDate(dtp.getDate());
                    break;
                case "455":
                    dates.setLastXRayDate(dtp.getDate());
                    break;
                case "471":
                    dates.setPrescriptionDate(dtp.getDate());
                    break;
                case "360":
                    dates.setDisabilityDate(dtp.getDate());
                    break;
                case "297":
                    dates.setLastWorkedDate(dtp.getDate());
                    break;
                case "296":
                    dates.setAuthorizedReturnWorkDate(dtp.getDate());
                    break;
                case "435":
                    dates.setAdmissionDate(dtp.getDate());
                    break;
                case "096":
                    dates.setDischargeDate(dtp.getDate());
                    break;
                case "090":
                    dates.setAssumedCareDate(dtp.getDate());
                    break;
                case "091":
                    dates.setRelinguishedCareDate(dtp.getDate());
                    break;
                case "444":
                    dates.setPNc1stContactDate(dtp.getDate());
                case "050":
                    dates.setRepricedReceivedDate(dtp.getDate());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Updates a FHIR {@link ClaimReferences} object with the given X12 837 REF segment.
     *
     * @param references the {@link ClaimReferences} to be updated.
     * @param ref an X12 837 REF segment.
     */
    public static void updateClaimReferences(ClaimReferences references, REF837 ref) {
        if (ref.isValid()) {
            switch (ref.getQualifier()) {
                case "G3":
                    references.setPredetermineId(ref.getId());
                    break;
                case "F5":
                    references.setMedicareVersionCode(ref.getId());
                    break;
                case "EW":
                    references.setMammgrphyCertNo(ref.getId());
                    break;
                case "9F":
                    references.setReferalNumber(ref.getId());
                    break;
                case "D9":
                    references.setClaimNumber(ref.getId());
                    break;
                default:
                    break;
            }
        }
    }
}
