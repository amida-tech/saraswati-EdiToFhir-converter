package edifhir.ConverterImplementation;

import java.util.ArrayList;
import java.util.List;

import com.imsweb.x12.*;
import com.imsweb.x12.Element;

import org.hl7.fhir.r4.model.*;
import ca.uhn.fhir.*;
import ch.qos.logback.core.joran.conditional.ElseAction;
import edifhir.model.Dtp;
import edifhir.model.HealthCoverage;
import edifhir.model.MemberLevelDetail;
import edifhir.model.Ref;
import edifhir.model.SponsorPayer;


public class MapObjects {
    
    public List<SponsorPayer> sponsorPayer(X12 x12File){
        
        ArrayList<SponsorPayer> sponsorPayer = new ArrayList<SponsorPayer>();
        final String sponsorLoopId = "1000";
        final String segN1Id = "N1";
        List<Loop> sponsorLoop = x12File.findLoop(sponsorLoopId);
        
        /*
            Loop through all existing loops and segment
            and map to appropriate pojo
        */
        for(Loop loop : sponsorLoop){
            for(Segment segment : loop){
                if(segment.getId().equals(segN1Id)){
                    SponsorPayer sP = new SponsorPayer();
                    sP.setEntityIdCode(segment.getElementValue("N101"));
                    sP.setName(segment.getElementValue("N102"));
                    sP.setIdCoderQualifier(segment.getElementValue("N103"));
                    sP.setIdCode(segment.getElementValue("N104"));
                    sP.setEntityRelationshipCode(segment.getElementValue("N105"));
                    sP.setEntityIdCode2(segment.getElementValue("N106"));
                    sponsorPayer.add(sP);
                }
            }
        }

        return sponsorPayer;
    }

    

    public HealthCoverage healthCoverage(X12 x12File){
        
        final String healthCoverageLoopId = "2300";
        final String hdId = "HD";
        final String dtpId = "DTP";
        final String refId = "REF";
        List<Loop> coverage = x12File.findLoop(healthCoverageLoopId);
        HealthCoverage healthCoverage = new HealthCoverage();

        /*
            Loop through all existing loops and segment
            and map to appropriate pojo
        */
        for(Loop loop : coverage){
            for(Segment segment : loop){
                if(segment.getId().equals(hdId)){
                    healthCoverage.setMaintenanceTypeCode(segment.getElementValue("H01"));
                    healthCoverage.setInsuranceLineCode(segment.getElementValue("H02"));
                    healthCoverage.setPlanCoverageDescription(segment.getElementValue("H03"));
                    healthCoverage.setCoverageLevelCode(segment.getElementValue("H04"));
                } else if(segment.getId().equals(dtpId)){
                    Dtp dtp = new Dtp(segment.getElementValue("DTP01"),segment.getElementValue("DTP02"),segment.getElementValue("DTP03"));
                    healthCoverage.setDtp(dtp);
                } else if(segment.getId().equals(refId)){
                    Ref ref = new Ref(segment.getElementValue("REF01"),segment.getElementValue("REF02"),segment.getElementValue("REF03"), segment.getElementValue("REF04"));
                    healthCoverage.setRef(ref);
                } else {
                    //Do nothing for now                    
                }
            }
        }

        return healthCoverage;
    }

    public MemberLevelDetail member(X12 x12File){
        final String memberLoopId = "2000";
        final String segINSId = "INS";
        final String segRefId = "REF";
        final String segDtpId = "DTP";
        List<Loop> memberLoop = x12File.findLoop(memberLoopId);
        MemberLevelDetail member = new MemberLevelDetail(); 
        
        /*
            Loop through all existing loops and segment
            and map to appropriate pojo
        */
        for(Loop loop : memberLoop){
            for(Segment segment : loop){
                if(segment.getId().equals(segINSId)){
                    //Address address = new Address(segment.getElementValue("INS01"), segment.getElementValue("INS01"));
                    member.setResponse(segment.getElementValue("INS01"));
                    member.setIndividualRelationshipCode(segment.getElementValue("INS02"));
                    member.setMaintenanceTypeCode(segment.getElementValue("INS03"));
                    member.setMaintenanceReasonCode(segment.getElementValue("INS04"));
                    member.setBenefitStatusCode(segment.getElementValue("INS05"));
                    member.setMedicarePlanCode(segment.getElementValue("INS06"));
                    member.setEligibilityReasonCode(segment.getElementValue("INS06-2"));
                    member.setEmploymentStatusCode(segment.getElementValue("INS08"));
                    member.setStudentStatusCode(segment.getElementValue("INS09"));
                    member.setResponseCode2(segment.getElementValue("INS10"));
                } else if(segment.getId().equals(segRefId)){
                    Ref ref = new Ref(segment.getElementValue("REF01"),segment.getElementValue("REF02"),segment.getElementValue("REF03"), segment.getElementValue("REF04"));
                    member.setRef(ref);
                } else if (segment.getId().equals(segDtpId)){
                    Dtp dtp = new Dtp(segment.getElementValue("DTP01"),segment.getElementValue("DTP02"),segment.getElementValue("DTP03"));
                    member.setDtp(dtp);
                } else {
                    //Do nothing for now
                }
            }
        }
        
        return member;
    }
    

}
