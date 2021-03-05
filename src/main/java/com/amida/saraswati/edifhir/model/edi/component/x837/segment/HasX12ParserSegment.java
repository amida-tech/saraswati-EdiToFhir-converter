package com.amida.saraswati.edifhir.model.edi.component.x837.segment;

import com.imsweb.x12.Segment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The interface contains a default method for mapping a
 * x12-parser segment object to an 837 segment object in
 * x12-837 package.
 *
 * It is implemented by all 837 segemnt classes.
 *
 * @author Warren Lin
 */

public interface HasX12ParserSegment {

    default void copy(Object thisClass, Segment x12Segment) {
        if (thisClass.getClass().getSimpleName().startsWith(x12Segment.getId())) {
            copyValue(thisClass, x12Segment);
        }
    }

    static void copyValue(Object thisClass, Segment x12Segment) {
        Method[] allMethods = thisClass.getClass().getMethods();
        Arrays.stream(allMethods).filter(m -> m.getName().startsWith("set"))
                .forEach(m -> {
                    String prop = m.getName().substring(3).toUpperCase();
                    try {
                        String value = x12Segment.getElementValue(prop);
                        if (value != null) {
                            m.invoke(thisClass, value);
                        }
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        // TODO: ignore for now.
                    }
                });
    }
}
