package com.amida.saraswati.edifhir.converter.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;

import org.hl7.fhir.r4.model.Organization.OrganizationContactComponent;

public class X12Contact {
    static public List<OrganizationContactComponent> toFhir(Loop billingProvider) {
        List<Segment> segments =  billingProvider.findSegment("PER");
        if (segments.size() < 1) {
            return null;
        }

        Set<String> names = new HashSet<String>();
        List<OrganizationContactComponent> result = new ArrayList<>();
        for (Segment segment: segments) {
            OrganizationContactComponent contactComponent = new OrganizationContactComponent();
            
            String name = segment.getElementValue("PER02");
            if (name != null && !name.isEmpty() && !names.contains(name)) {    
                contactComponent.addChild(name);
                names.add(name);
                result.add(contactComponent);
            }
        }
    
        return result;
    }
}
