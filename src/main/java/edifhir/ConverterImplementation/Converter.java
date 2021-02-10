package edifhir.ConverterImplementation;

import java.util.List;

import com.imsweb.x12.*;
import org.hl7.fhir.r4.model.*;
import ca.uhn.fhir.*;
import edifhir.model.SponsorPayer;


public class Converter {
    
    public void sponsorName(X12 x12File){
        
        //Find loop/loops that indicate Sponsor name 1000A
        List<Loop> Sponsor = x12File.findLoop("1000A");
        
    }

    public void payerName(X12 x12File){
        List<Loop> payer = x12File.findLoop("2000");
    }

    public void healthCoverage(X12 x12File){
        
        //Find loop/loops that indicate Health Coverage
        List<Loop> coverage = x12File.findLoop("2300");

        /*
            Loop through all existing loops and segment
            create resource accordingly based on pojo
        */
        for(Loop loop : coverage){
            Coverage health = new Coverage();
            for(Segment s : loop){
                SponsorPayer payer = new SponsorPayer();
                payer.setEntityIdCode(s.getElementValue("01"));
                payer.setEntityIdCode2(s.getElementValue("02"));
                payer.setIdCode(s.getElementValue("03"));
                health.addContract();
                health.addExtension();
                health.addPayor();
                health.addCostToBeneficiary();
            }
        }
    }


    public void converter(X12 file){
        //Bundle all resources and create transaction
    }

}
