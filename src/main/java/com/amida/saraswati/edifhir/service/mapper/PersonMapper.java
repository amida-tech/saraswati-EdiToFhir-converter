package com.amida.saraswati.edifhir.service.mapper;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.NM1837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.PER837;
import com.amida.saraswati.edifhir.util.X12Util;
import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Person;

import java.util.Arrays;

/**
 * Maps a loop to a FHIR Person resource.
 *
 * @author Warren Lin
 */
public class PersonMapper {

    private static final String[] CONCEPT_CODES = {
            "S", // submitter person
            "R"  // receiver person
    };

    private static final String[] CONCEPT_VALUES = {
            "X12 837 Submitter",
            "X12 837 Receiver"
    };

    private static final String SUBMITTER_ID_VALUE = "001";
    private static final String RECEIVER_ID_VALUE = "001";

    public enum PERSON_TYPE {
        SUBMITTER, RECEIVER
    }

    public static Person mapSubmitterToPerson(Loop loop1000A) throws X12ToFhirException, InvalidDataException {
        Segment nm1 = loop1000A.getSegment("NM1");
        if (nm1 == null) {
            throw new InvalidDataException("NM1 is missing in loop 1000A");
        }
        NM1837 nm1837 = new NM1837(nm1);
        Person person = new Person();
        HumanName name = X12Util.getHumanName(nm1837);
        person.setName(Arrays.asList(name));
        person.addIdentifier()
                .setType(getPersonType(PERSON_TYPE.SUBMITTER))
                .setValue(SUBMITTER_ID_VALUE);

        Segment per = loop1000A.getSegment("PER");
        if (per == null) {
            throw new InvalidDataException("PER is missing in loop 1000A");
        }
        PER837 per837 = new PER837(per);
        person.setName(Arrays.asList(X12Util.getHumanName(per837)));
        person.setTelecom(X12Util.getContactNumbers(per837));
        person.setId(loop1000A.getId());
        return person;
    }

    public static Person mapReceiverToPerson(Loop loop1000B) throws InvalidDataException {
        Segment nm1 = loop1000B.getSegment("NM1");
        if (nm1 == null) {
            throw new InvalidDataException("NM1 is missing in loop 1000A");
        }
        NM1837 nm1837 = new NM1837(nm1);
        Person person = new Person();
        HumanName name = X12Util.getHumanName(nm1837);
        person.setName(Arrays.asList(name));
        person.addIdentifier()
                .setType(getPersonType(PERSON_TYPE.RECEIVER))
                .setValue(RECEIVER_ID_VALUE);
        person.setId(loop1000B.getId());
        return person;
    }

    private static CodeableConcept getPersonType(PERSON_TYPE type) {
        switch (type) {
            case SUBMITTER:
                return X12Util.getCodeConcept(CONCEPT_CODES[0], CONCEPT_VALUES[0]);
            case RECEIVER:
                return X12Util.getCodeConcept(CONCEPT_CODES[1], CONCEPT_VALUES[1]);
            default:
                return null;
        }
    }
}
