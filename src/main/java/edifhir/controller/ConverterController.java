package edifhir.controller;

import java.nio.charset.StandardCharsets;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
//import com.imsweb.x12.*;
import org.hl7.fhir.r4.model.*;



//@EnableBinding(Processor.class)
public class ConverterController {

    //@StreamListener(Processor.INPUT)
    //@SendTo(Processor.OUTPUT)
    public void receive(@Payload byte[] bytes){
        String message = new String(bytes, StandardCharsets.UTF_8);
    }

}
