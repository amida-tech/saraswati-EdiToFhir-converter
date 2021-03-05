package com.amida.saraswati.edifhir.model.edi.component;

import com.amida.saraswati.edifhir.exception.X12ParseException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The object represents a demoghrapic segment in X12-837.
 *
 * @author Warren Lin
 */

public interface HasDate {
    default Date getX12Date(String format, String dateValue) throws ParseException, X12ParseException {
        return formatX12Date(format, dateValue);
    }

    static Date formatX12Date(String format, String dateValue) throws ParseException, X12ParseException {
        if ("D8".equalsIgnoreCase(format)) {
            DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            return fmt.parse(dateValue);
        }
        throw new X12ParseException("unsupported datetime format " + format);
    }
}
