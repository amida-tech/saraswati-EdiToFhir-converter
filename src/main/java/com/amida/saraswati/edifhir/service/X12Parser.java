package com.amida.saraswati.edifhir.service;

import com.amida.saraswati.edifhir.exception.X12ParseException;
import com.amida.saraswati.edifhir.model.edi.X12Transaction;

import java.util.List;

/**
 * The interface defines the services for parsing EDI x12 stream to
 * a list of X12Transaction objects.
 */
public interface X12Parser {
    List<X12Transaction> parse(Class x12Class, String message) throws X12ParseException;
}
