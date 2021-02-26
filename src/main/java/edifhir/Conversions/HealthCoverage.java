package edifhir.Conversions;

import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import org.hl7.fhir.r4.model.Coverage;
import org.hl7.fhir.r4.model.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * HealthCoverage maps directly to Loop 2300 in EDI 834
 */

public class HealthCoverage {
    
    public Coverage createHealthCoverage(Loop loop2300){
        Loop coverage = loop2300.getLoop("2300");
        List<Segment> segments =  loop2300.findSegment("HD");
        if (segments.size() < 1) {
            return null;
        }

        Set<String> names = new HashSet<String>();
        List<Coverage> result = new ArrayList<>();
        Coverage healthCoverage = new Coverage();


        for (Segment segment: segments) {

            if(segment.getElementValue("HD01").contains("HD")){
                String name = segment.getElementValue("HD02");
                String insuranceLineCode = segment.getElementValue("HD03");
                String planCovergaeDescription = segment.getElementValue("HD04");
                healthCoverage.addChild(name);
                healthCoverage.addChild(insuranceLineCode);
                healthCoverage.addChild(planCovergaeDescription);
            }else if(segment.getElementValue("DTP01").contains("DTP")){
                String beginEndDate = segment.getElementValue("DTP01");
                healthCoverage.addChild(beginEndDate);
            } else if(segment.getElementValue("REF01").contains("REF")){
                String refIdHCPNum = segment.getElementValue("REF");
                healthCoverage.addChild(refIdHCPNum);
            } else {
                String identifier = segment.getElementValue("HD07");
                Identifier id = new Identifier();
                id.setSystem("http://hl7.org/fhir/sid/us-npi");
                id.setValue(identifier);
                healthCoverage.addChild(identifier);

            }
            result.add(healthCoverage);
        }  
        return healthCoverage;      
    }

}
