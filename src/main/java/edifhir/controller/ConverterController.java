package edifhir.controller;

import java.nio.charset.StandardCharsets;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
//import com.imsweb.x12.*;
import org.hl7.fhir.r4.model.*;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@EnableBinding(Processor.class)
public class ConverterController {

    private FhirContext ctx = FhirContext.forR4();
    private String serverBase = "http://localhost:8080/fhir";
    private IGenericClient client = ctx.newRestfulGenericClient(serverBase);
    private Resource fhirResource = new ClassPathResource("fhirbody.json");

    public FhirContext getCtx() {
        return this.ctx;
    }

    public void setCtx(FhirContext ctx) {
        this.ctx = ctx;
    }

    public String getServerBase() {
        return this.serverBase;
    }

    public void setServerBase(String serverBase) {
        this.serverBase = serverBase;
    }

    public IGenericClient getClient() {
        return this.client;
    }

    public void setClient(IGenericClient client) {
        this.client = client;
    }

    public Resource getFhirResource() {
        return this.fhirResource;
    }

    public void setFhirResource(Resource fhirResource) {
        this.fhirResource = fhirResource;
    }
    

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public void receive(@Payload byte[] bytes){
        String message = new String(bytes, StandardCharsets.UTF_8);
    }

}
