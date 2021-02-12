package com.amida.saraswati.edifhir.converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.imsweb.x12.Loop;
import com.imsweb.x12.reader.X12Reader;
import com.imsweb.x12.reader.X12Reader.FileType;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class X12834ParserTest {
    private Resource x12834Example1 = new ClassPathResource("x12-837-example-1.txt");

    @Test
    public void Example1ReadTest() throws Exception {
        X12Reader reader = new X12Reader(FileType.ANSI837_5010_X222, x12834Example1.getInputStream());
        List<Loop> loops = reader.getLoops();

        assertTrue(loops.size() > 0);

        Loop root = loops.get(0);

        List<Loop> billingProviders = root.findAllLoops("2000A");
        
        assertTrue(billingProviders.size() > 0);
    } 
}
