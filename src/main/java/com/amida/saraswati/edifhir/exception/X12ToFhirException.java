/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.exception */
package com.amida.saraswati.edifhir.exception;

public class X12ToFhirException extends Exception {
    private static final String DEFAULT_MESSAGE = "X12toFhirException";

    public X12ToFhirException() {
        super(DEFAULT_MESSAGE);
    }

    public X12ToFhirException(String msg) {
        super(msg);
    }

    public X12ToFhirException(String msg, Throwable e) {
        super(msg, e);
    }

}
