package edifhir.converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.amida.saraswati.edifhir.converter.mapper.X12BillingProvider;
import com.imsweb.x12.Loop;
import com.imsweb.x12.reader.X12Reader;
import com.imsweb.x12.reader.X12Reader.FileType;

import org.hl7.fhir.r4.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class X12834ParserTest {
    private Resource x12834Example1 = new ClassPathResource("x12-837-example-1.txt");
    private FhirContext ctx = FhirContext.forR4();

    @Test
    public void Example1ReadTest() throws Exception {
        X12Reader reader = new X12Reader(FileType.ANSI837_5010_X222, x12834Example1.getInputStream());
        List<Loop> loops = reader.getLoops();

        assertTrue(loops.size() > 0);

        Loop root = loops.get(0);

        List<Loop> billingProviders = root.findAllLoops("2000A");
        assertTrue(billingProviders.size() > 0);

        Loop loop2000A = billingProviders.get(0);
        org.hl7.fhir.r4.model.Resource result = X12BillingProvider.toFhir(loop2000A);
        assertTrue(result instanceof Organization);
    
        IParser parser = ctx.newJsonParser();
        String json = parser.encodeResourceToString(result);
        System.out.println(json);
    } 
}
