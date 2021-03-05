package com.amida.saraswati.edifhir.exception;

/**
 * The exception is for x12 parsing process.
 *
 * @author Warren Lin
 */
public class X12ParseException extends Exception {

    public X12ParseException() {
        super();
    }

    public X12ParseException(String msg) {
        super(msg);
    }

    public X12ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
