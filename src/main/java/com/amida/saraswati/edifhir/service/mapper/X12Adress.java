package com.amida.saraswati.edifhir.service.mapper;

import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import org.hl7.fhir.r4.model.Address;

public class X12Adress {
    static public Address toFhir(Loop loop) {
        Segment streetAddress = loop.getSegment("N3");
        Segment cityStateZip = loop.getSegment("N4");
        return getAddress(streetAddress, cityStateZip);
    }

    public static Address getAddress(Segment streetAddress, Segment cityStateZip) {
        if (streetAddress == null && cityStateZip == null) {
            return null;
        }
        Address address = new Address();
        if (streetAddress != null) {
            String line1 = streetAddress.getElementValue("N301");
            address.addLine(line1);
            if (line1 != null && !line1.isEmpty()) {
                String line2 = streetAddress.getElementValue("N302");
                if (line2 != null && !line2.isEmpty()) {
                    address.addLine(line2);
                }
            }
        }
        if (cityStateZip != null) {
            String city = cityStateZip.getElementValue("N401");
            if (city != null && !city.isEmpty()) {
                address.setCity(city);
            }
            String state = cityStateZip.getElementValue("N402");
            if (state != null && !state.isEmpty()) {
                address.setState(state);
            }
            String zip = cityStateZip.getElementValue("N403");
            if (zip != null && !zip.isEmpty()) {
                address.setPostalCode(zip);
            }
        }
        return address;
    }    
}
