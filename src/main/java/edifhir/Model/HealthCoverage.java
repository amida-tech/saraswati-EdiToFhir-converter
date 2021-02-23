package edifhir.Model;

import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import org.hl7.fhir.r4.model.Coverage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            if (name != null && !name.isEmpty() && !names.contains(name)) {    
                healthCoverage.addChild(name);
                //names.add(name);
                //result.add(contactComponent);
            }
        }  
        return result;      
    }

}
