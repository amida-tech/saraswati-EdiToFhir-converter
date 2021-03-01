package com.amida.saraswati.edifhir.service.mapper;

import com.amida.saraswati.edifhir.exception.InvalidDataException;
import com.amida.saraswati.edifhir.exception.X12ToFhirException;
import com.amida.saraswati.edifhir.model.fhir.Fhir837;
import com.amida.saraswati.edifhir.util.X12ParserUtil;
import com.imsweb.x12.Loop;
import com.imsweb.x12.Segment;
import com.imsweb.x12.reader.X12Reader;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps the X12Reader to a fhir bundle.
 *
 * @author Warren Lin
 */

@Slf4j
@Component
@NoArgsConstructor
public class X837Mapper {
    private static final String ST_LOOP = "ST_LOOP";
    private static final String ST_SEGMANT = "ST";
    private static final String X12_837 = "837";
    private static final String ST_ELEMENT = "ST01";
    private static final String HEADER_LOOP = "HEADER";
    private static final String DETAIL_LOOP = "DETAIL";
    private static final String LOOP_2000A = "2000A";  // provider loop
    private static final String LOOP_2000B = "2000B";  // subscriber loop
    private static final String LOOP_2300 = "2300";  // claim loop

    /**
     * Maps the given X12Reader to a list of bundles. Each 837 ST loop will
     * be mapped to one FHIR bundle (FHIR837 object).
     *
     * @param reader a X12Reader object.
     * @return a list of FHIR837 objects.
     * @throws X12ToFhirException error occurs.
     * @throws InvalidDataException reader contains invalid data.
     */
    public List<Fhir837> getFhirBundles(X12Reader reader) throws X12ToFhirException, InvalidDataException {

        List<Loop> loops = reader.getLoops();
        X12ParserUtil.logLoopTravise(loops, 1, false);
        List<Loop> loop837s = get837Loop(loops);
        if (loop837s.isEmpty()) {
            throw new InvalidDataException("No 837 is found.");
        }

        List<Pair<Fhir837, X12ToFhirException>> result = loop837s.stream()
                .map(this::getFhir837).collect(Collectors.toList());
        Optional<Pair<Fhir837, X12ToFhirException>> bad =
                result.stream().filter(p -> p.getRight() != null).findFirst();
        if (bad.isPresent()) {
            throw new X12ToFhirException("X12-837 FHIR mapping error", bad.get().getRight());
        }
        return result.stream().map(Pair::getLeft).collect(Collectors.toList());
     }

    /**
     * Maps a x12-837 loop to a FHIR bundle. A pair of a FHIR bundle and exception
     * will be returned. The left element of the pair will be the mapped FHIR bundle
     * object, Fhir837. If exception occurs during the mapping, the exception will
     * be returned as the right element of the returning pair.
     *
     * @param loop837 an X12-837 Transaction loop.
     * @return a pair of Fhir837 and X12ToFhirException.
     */
    private Pair<Fhir837, X12ToFhirException> getFhir837(Loop loop837) {
        Fhir837 result = new Fhir837();
        X12ToFhirException exception;

        Loop headerLoop = loop837.getLoop(HEADER_LOOP);
        // TODO: mapper submitter to person and receiver to person

        Loop detailLoop = loop837.getLoop(DETAIL_LOOP);
        if (detailLoop == null) {
            exception = new X12ToFhirException("Missing detail loop");
            return Pair.of(null, exception);
        }
        List<Loop> loop2000s = detailLoop.getLoops();
        List<Loop> loop2000As = loop2000s.stream()
                .filter(l -> LOOP_2000A.equals(l.getId()))
                .collect(Collectors.toList());
        for (Loop loop : loop2000As) {
            // Note: x12-parser puts all other 2000 subloops, e.g., 2000B, 2000C in 2000A loop
            // TODO: what happens with a transaction contains more than one 2000A loop?
            Resource billingProvider = X12BillingProvider.toFhir(loop);
            if (billingProvider != null) {
                result.addResource(billingProvider);
            }
            List<Loop> subscriberLoops = loop.getLoops().stream()
                    .filter(l -> l.getId().equals(LOOP_2000B))
                    .collect(Collectors.toList());

            List<Patient> subscribers = subscriberLoops.stream()
                        .map(SubscriberMapper::mapSubscriber)
                        .collect(Collectors.toList());
            subscribers.forEach(result::addResource);

            for (Loop loop2000b : subscriberLoops) {
                List<Loop> claimLoops = loop2000b.getLoops().stream()
                        .filter(l -> LOOP_2300.equals(l.getId()))
                        .collect(Collectors.toList());
                List<Claim> claims = claimLoops.stream()
                        .map(ClaimMapper::mapClaim).collect(Collectors.toList());
                claims.forEach(result::addResource);
            }
        }
        // TODO: map more segments.
        return Pair.of(result, null);
    }

    private List<Loop> get837Loop(List<Loop> loops) {
        List<Loop> loop837s = new ArrayList<>();
        for (Loop loop : loops) {
            if (!is837(loop)) {
                if (loop.getLoops().isEmpty()) {
                    break;
                }
                return get837Loop(loop.getLoops());
            }
            loop837s.add(loop);
        }
        return loop837s;
    }

    private boolean is837(Loop loop) {
        if (ST_LOOP.equals(loop.getId())) {
            Optional<Segment> segment = loop.getSegments().stream()
                    .filter(s -> s.getId().equals(ST_SEGMANT)).findFirst();
            if (segment.isPresent()) {
                Segment stSeg = segment.get();
                return X12_837.equals(stSeg.getElement(ST_ELEMENT).getValue());
            }
        }
        return false;
    }

}
