/* warren created on 2/25/21 inside the package - com.amida.saraswati.edifhir.service.mapper */
package com.amida.saraswati.edifhir.service.mapper;

import com.amida.saraswati.edifhir.model.edi.component.x837.segment.DMG837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.NM1837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.SBR837;
import com.amida.saraswati.edifhir.util.X12Util;
import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.*;

import java.util.Collections;
import java.util.Date;

/**
 * Maps an X12 2000B loop for subscriber to a FHIR Patient resource.
 *
 * @author Warren Lin
 */
@Slf4j
public class SubscriberMapper {

    public static Patient mapSubscriber(Loop loop2000B) {
        Patient subscriber = getSubscriberInfo(loop2000B);

        Segment pat = loop2000B.getSegment("PAT");
        // TODO: map patient when pat is available.

        Loop loop2010BA = loop2000B.getLoop("2010BA");  // subscriber detail

        if (pat == null) {
            subscriber = addContact(subscriber, loop2010BA, null);
        } else {
            subscriber = addContact(subscriber, loop2010BA, "subscriber");
        }

        Loop loop2010BB = loop2000B.getLoop("2010BB");  // payer
        subscriber = addContact(subscriber, loop2010BB, "payer");

        return subscriber;
    }


    public static Patient mapPatient(Loop loop2000C) {
        Patient patient = new Patient();
        // TODO: to be finished.
        return patient;
    }


    private static Patient getSubscriberInfo(Loop loop2000B) {
        Patient subscriber = new Patient();
        subscriber.setId("subscriber");

        Segment sbr = loop2000B.getSegment("SBR");
        SBR837 sbr837 = new SBR837(sbr);
        Extension ext = subscriber.addExtension();
        ext.setId("Insurance Policy");
        CodeType insPolicyInfo = new CodeType(sbr837.getPlanName());
        ext.setValue(insPolicyInfo);
        ext = subscriber.addExtension();
        ext.setId("subscriber relation");
        CodeType relation = new CodeType(sbr837.getPayerResponsibility() + ":" + sbr837.getRelationShip());
        ext.setValue(relation);
        ext = subscriber.addExtension();
        ext.setId("Claim Filing Type");
        CodeType filingType = new CodeType(sbr837.getClaimFilingId());
        ext.setValue(filingType);

        subscriber.addIdentifier()
                .setType(X12Util.getCodeConcept("um", "claim filing id"))
                .setValue(sbr837.getClaimFilingId());
        if (!StringUtils.isEmpty(sbr837.getPolicyGroupNumber())) {
            subscriber.addIdentifier()
                    .setType(X12Util.getCodeConcept("MB", "Policy Number"))
                    .setValue(sbr837.getPolicyGroupNumber());
        }
        // TODO: for mapping subscriber to a Patient, where to put payerResponsibility code
        //  plan name, relationship?

        return subscriber;
    }

    private static Patient addContact(@NonNull Patient subscriber, Loop loop, String type) {
        if (loop != null) {
            HumanName name;
            Segment contactNM = loop.getSegment("NM1");
            if (contactNM != null) {
                name = X12Util.getHumanName(new NM1837(contactNM));
                Segment n3 = loop.getSegment("N3");
                Segment n4 = loop.getSegment("N4");
                Address address = null;
                if (n3 != null) {
                    address = X12Adress.getAddress(n3, n4);
                }
                if (type == null) {
                    addPatientNameNAddress(subscriber, name, address);
                    setBDayNGender(subscriber, loop.getSegment("DMG"));
                } else {
                    addContactNameNAddress(subscriber, name, address, type);
                }

            }
        }
        return subscriber;
    }

    private static void setBDayNGender(Patient subscriber, Segment dmg) {
        if (dmg != null) {
            DMG837 dmg837 = new DMG837(dmg);
            Date bDay = dmg837.getBirthDate();
            if (bDay != null) {
                subscriber.setBirthDate(bDay);
            }
            subscriber.setGender(X12Util.getGenderCode(dmg837.getGenderCode()));
        }
    }

    private static void addPatientNameNAddress(@NonNull Patient subscriber,
                                               @NonNull HumanName name,
                                               Address address) {
        subscriber.setName(Collections.singletonList(name));
        if (address != null) {
            subscriber.setAddress(Collections.singletonList(address));
        }
    }

    private static void addContactNameNAddress(@NonNull Patient subscriber,
                                               @NonNull HumanName name,
                                               Address address,
                                               String type) {
        if (address != null) {
            subscriber.addContact()
                    .setName(name)
                    .setAddress(address)
                    .setId(type);  // TODO: set type, i.e., payer, as Id.
        } else {
            subscriber.addContact()
                    .setName(name)
                    .setId(type);
        }
    }
}
