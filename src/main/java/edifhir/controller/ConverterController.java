package edifhir.controller;

import java.nio.charset.StandardCharsets;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import com.imsweb.x12.*;
import org.hl7.fhir.*;
import ca.uhn.fhir.*;

@EnableBinding(Processor.class)
public class ConverterController {


    public void sponsorName(Loop loop){
        //Create a Sponsor resource
        
    }

    public void payerName(Loop loop){
        //Create a Payer resource
    }



    public void converter(X12 file){
        //Bundle all resources and create transaction
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public void receive(@Payload byte[] bytes){
        String message = new String(bytes, StandardCharsets.UTF_8);
    }

}
