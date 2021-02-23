package edifhir.Model;

import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import org.hl7.fhir.r4.model.Coverage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * HealthCoverage maps directly to Loop 2300 in EDI 834
 */

public class HealthCoverage {
    
    public List<Coverage> createHealthCoverage(Loop coverage){
        List<Segment> segments =  coverage.findSegment("HD");
        if (segments.size() < 1) {
            return null;
        }

        Set<String> names = new HashSet<String>();
        List<Coverage> result = new ArrayList<>();
        for (Segment segment: segments) {
            Coverage healthCoverage = new Coverage();

            String name = segment.getElementValue("HD02");
            String insuranceLineCode = segment.getElementValue("HD03");
            String planCovergaeDescription = segment.getElementValue("HD04");
            String beginEndDate = segment.getElementValue("HD05");
            String refIdHCPNum = segment.getElementValue("HD06");
            String identifier = segment.getElementValue("HD07");
            
            if (segment.getElementValue() != null && !segment.getElementValue().isEmpty()) {    
                healthCoverage.addChild(name);
                healthCoverage.addChild(insuranceLineCode);
                healthCoverage.addChild(planCovergaeDescription);
                healthCoverage.addChild(beginEndDate);
                healthCoverage.addChild(refIdHCPNum);
                healthCoverage.addChild(identifier);
                //names.add(name);
                //result.add(contactComponent);
            }
        }  
        return result;      
    }

}
