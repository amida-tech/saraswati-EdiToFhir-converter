package com.amida.saraswati.edifhir.service.mapper;

import com.imsweb.x12.Loop;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class X12BillingProvider {
    static public Resource toFhir(Loop loop2000A) {
        Loop name = loop2000A.getLoop("2010AA");
        String entityType = name.getElement("NM1", "NM102");
        if (entityType.equals("1")) {
            Practitioner practitioner = new Practitioner();
            return practitioner;
        } else if (entityType.equals("2")) {
            Organization organization = new Organization();
            String orgName = name.getElement("NM1", "NM103");
            if (orgName != null && !orgName.isEmpty()) {
                organization.setName(orgName);
            }

            List<Identifier> identifiers = new ArrayList<>();
            String idCodeQualifier = name.getElement("NM1", "NM108");
            if (idCodeQualifier != null && idCodeQualifier.equals("XX")) {
                String id = name.getElement("NM1", "NM109");
                if (id != null && !id.isEmpty()) {
                    Identifier identifier = new Identifier();
                    identifier.setSystem("http://hl7.org/fhir/sid/us-npi");
                    identifier.setValue(id);
                    Coding coding = new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "NPI", "National provider identifier");
                    CodeableConcept cc = new CodeableConcept(coding);
                    identifier.setType(cc);
                    identifiers.add(identifier);
                }
            }
            String idCodeQualifier2 = name.getElement("REF", "REF01");
            if (idCodeQualifier2 != null && idCodeQualifier2.equals("EI")) {
                String id = name.getElement("REF", "REF02");
                if (id != null && !id.isEmpty()) {
                    Identifier identifier = new Identifier();
                    identifier.setValue(id);
                    Coding coding = new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "TAX", "Tax ID Number");
                    CodeableConcept cc = new CodeableConcept(coding);
                    identifier.setType(cc);
                    identifiers.add(identifier);
                }               
            }
            if (identifiers.size() > 0) {
                organization.setIdentifier(identifiers);                
            }

            Address address = X12Adress.toFhir(name);
            if (address != null) {
                organization.setAddress(Arrays.asList(address));
            }
            return organization;
        }
        return null;
    }    
}
