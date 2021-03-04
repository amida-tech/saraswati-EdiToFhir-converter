package com.amida.saraswati.edifhir.util;

import com.amida.saraswati.edifhir.model.edi.component.x837.segment.DTP837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.HI837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.PWK837;
import com.amida.saraswati.edifhir.model.edi.component.x837.segment.REF837;
import com.imsweb.x12.Loop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
    public static String loopTravise(List<Loop> loops, int indent, boolean showSegments) {
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
