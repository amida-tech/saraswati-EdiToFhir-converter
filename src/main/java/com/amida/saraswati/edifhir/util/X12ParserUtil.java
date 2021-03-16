package com.amida.saraswati.edifhir.util;

import com.amida.saraswati.edifhir.controller.X12EDIController;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.DTP837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.HI837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.PWK837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.REF837;
import com.amida.saraswati.edifhir.model.x12passer.X12LoopInfo;
import com.amida.saraswati.edifhir.model.x12passer.X12SegmentInfo;
import com.imsweb.x12.Loop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains some utilities for processing x12-parser data.
 *
 * @author warren
 */

@Slf4j
public class X12ParserUtil {

    /**
     * Returns the loop retrieval information for the given set of x12-parser's loops.
     *
     * @param loops a list of X12-parser loops.
     * @param indent The top level loop indentation in the returned text.
     * @param showSegments also retrieval segments when true.
     * @return a text of the retrieval information.
     */
    public static String loopTravise(List<Loop> loops, int indent,
                                     boolean showSegments) {
        StringBuilder loogTrace = new StringBuilder();
        loops.forEach(l -> {
            loogTrace.append(
                    String.format("%s loop id: %s, subloop count: %s, segment count: %s",
                            StringUtils.repeat(' ', indent),
                            l.getId(), l.getLoops().size(), l.getSegments().size())
            ).append("\n");
            if (showSegments) {
                l.getSegments().forEach(s ->
                        loogTrace.append(
                                String.format("%s Segment: %s, field count: %s",
                                        StringUtils.repeat(' ', indent + 2),
                                        s.getId(), s.size()))
                                .append("\n")
                );
            }
            loogTrace.append(loopTravise(l.getLoops(), indent + 2, showSegments));
        });
        return loogTrace.toString();
    }

    /**
     * Logs the x12-parser loop retrieval inforamtion.
     *
     * @param loops a list of X12-parser loops.
     * @param indent The top level loop indentation in the returned text.
     * @param showSegments also retrieval segments when true.
     */
    public static void logLoopTravise(List<Loop> loops, int indent, boolean showSegments) {
        loops.forEach(l -> {
            log.info("{} loop id: {}, subloop count: {}, segment count: {}",
                    StringUtils.repeat(' ', indent),
                    l.getId(), l.getLoops().size(), l.getSegments().size());
            if (showSegments) {
                l.getSegments().forEach(s ->
                        log.info("{} Segment: {}, field count: {}",
                                StringUtils.repeat(' ', indent + 2),
                                s.getId(), s.size()));
            }
            logLoopTravise(l.getLoops(), indent + 2, showSegments);
        });
    }

    public static List<X12LoopInfo> loopTraviseWithInfo(List<Loop> loops, X12EDIController.X12DATA_TYPE type)
    {
        return loops.stream().map(l -> mapToLoopInfo(type, l)).collect(Collectors.toList());
    }

    private static X12LoopInfo mapToLoopInfo(X12EDIController.X12DATA_TYPE type, Loop l) {
        X12LoopInfo loopInfo = new X12LoopInfo();
        List<X12SegmentInfo> segments = new ArrayList<>();
        loopInfo.setLoopName(l.getId());
        loopInfo.setDescription(getLoopDescrition(l.getId(), type));
        loopInfo.setFhirMappingInfo(getLoopFhirMapInfo(l.getId(), type));
        l.getSegments().forEach(s -> segments.add(new X12SegmentInfo(s.getId())));
        String segmentlist =
                segments.stream().map(X12SegmentInfo::getSegmentName).collect(Collectors.joining(", "));
        loopInfo.setSegments(segments);
        loopInfo.setSegmanetNames(segmentlist);
        List<X12LoopInfo> subloops = loopTraviseWithInfo(l.getLoops(), type);
        loopInfo.setSubloops(subloops);
        return loopInfo;
    }

    private static String getLoopDescrition(String loopId, X12EDIController.X12DATA_TYPE dataType) {
        switch (dataType) {
            case EDI835:
                return get835LoopDescription(loopId);
            case EDI837:
                return get837LoopDescription(loopId);
            case EDI834:
                return get834LoopDescription(loopId);
            default:
                return "";
        }
    }

    private static String getLoopFhirMapInfo(String loopId, X12EDIController.X12DATA_TYPE dataType) {
        switch (dataType) {
            case EDI835:
                return "";  // TODO: to be finished.
            case EDI837:
                return get837LoopFhirMapInfo(loopId);
            case EDI834:
                return ""; // TODO: to be finished.
            default:
                return "";
        }
    }

    private static String get837LoopDescription(String loopId) {
        switch (loopId) {
            case "1000A":
                return "Claim Submitter name and contact information";
            case "1000B":
                return "Individual or organization name for the receiver";
            case "2000A":
                return "Billing Provider Information";
            case "2000AA":
                return "Billing provider name";
            case "2000AB":
                return "Pay-to address.";
            case "2000B":
                return "Subscriber and patient information";
            case "2010BA":
                return "Subscriber name";
            case "2010BB":
                return "Payer name";
            case "2000C":
                return "Patient information";
            case "2010CA":
                return "Patient name";
            case "2300":
                return "Claim information";
            case "2310A":
                return "Referring Provider Name";
            case "2310B":
                return "Rendering Provider Name";
            case "2310C":
                return "Service Facility Location";
            case "2310D":
                return "Supervising Provider Name";
            case "2310E":
                return "Ambulance Pick-up Location";
            case "2310F":
                return "Ambulance drop-off Location";
            case "2400":
                return "Service Line Number";
            case "2410":
                return "Drug information";
            case "2420A":
                return "Rendering Provider Name";
            case "2420B":
                return "Purchased Service Provider Name";
            case "2420C":
                return "Service Facility Location Name";
            case "2420D":
                return "Supervising Provider Name";
            case "2420E":
                return "Ordering Provider Name";
            case "2420F":
                return "Referring Provider Name";
            case "2420G":
                return "Ambulance Pick-up Location";
            case "2420H":
                return "Ambulance drop-off Location";
            case "2440":
                return "Form Identification Code";
            default:
                return "";
        }
    }

    private static String get834LoopDescription(String loopId) {
        return "";  // TODO: finish it later.
    }

    private static String get835LoopDescription(String loopId) {
        return "";  // TODO: finish it later.
    }

    private static String get837LoopFhirMapInfo(String loopId) {
        switch (loopId) {
            case "1000A":
                return "Map Submitter to FHIR Person resource.";
            case "1000B":
                return "Map Receiver to FHIR Person resource.";
            case "2000A":
                return "Map Billing Privider to FHIR Practitioner or Organization.";
            case "2000AA":
                return "Map to FHIR Practitioner/Organization name, address, id.";
            case "2000AB":
                return "Pay-to address not mapped.";
            case "2000B":
                return "Map to FHIR Patient resource.";
            case "2010BA":
                return "Add to FHIR Patient contact as subscriber.";
            case "2010BB":
                return "Add to FHIR Patient contact as payer.";
            case "2000C":
                return "Patient information not mapped.";
            case "2010CA":
                return "Patient name not mapped.";
            case "2300":
                return "Map to FHIR Claim resource.";
            case "2310A":
                return "Referring Privider Name not mapped";
            case "2310B":
                return "Rendering Provider Name not mapped";
            case "2310C":
                return "Service Facility Location not mapped";
            case "2310D":
                return "Supervising Provider Name not mapped";
            case "2310E":
                return "Ambulance Pick-up Location not mapped";
            case "2310F":
                return "Ambulance drop-off Location not mapped";
            case "2400":
                return "Map to FHIR Claim.ItemComponent.";
            case "2410":
                return "Drug information not mapped";
            case "2420A":
                return "Redering Provider Name not mapped";
            case "2420B":
                return "Pruchased Service Provider Name not mapped";
            case "2420C":
                return "Service Facility Location Name not mapped";
            case "2420D":
                return "Supervising Provider Name not mapped";
            case "2420E":
                return "Ordering Provider Name not mapped";
            case "2420F":
                return "Referring Provider Name not mapped";
            case "2420G":
                return "Ambulance Pick-up Location not mapped";
            case "2420H":
                return "Ambulance drop-off Location not mapped";
            case "2440":
                return "Form Identification Code not mapped";
            default:
                return "";
        }
    }

    // Get X12Reader loop elements
    ///////////////////////////////////////////////////////

    public static List<DTP837> getLoop2300Dtps(Loop loop2300) {
        return loop2300.getSegments().stream().filter(s -> s.getId().equals("DTP"))
                .map(DTP837::new).collect(Collectors.toList());
    }

    public static List<PWK837> getLoop2300Pwks(Loop loop2300) {
        return loop2300.getSegments().stream().filter(s -> s.getId().equals("PWK"))
                .map(PWK837::new).collect(Collectors.toList());
    }

    public static List<REF837> getLoop2300Refs(Loop loop2300) {
        return loop2300.getSegments().stream().filter(s -> s.getId().equals("REF"))
                .map(REF837::new).collect(Collectors.toList());
    }

    public static List<HI837> getLoop2300HIs(Loop loop2300) {
        return loop2300.getSegments().stream().filter(s -> s.getId().equals("HI"))
                .map(HI837::new).collect(Collectors.toList());
    }
}
