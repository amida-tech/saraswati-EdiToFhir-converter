package com.amida.saraswati.edifhir.exception;

public class EdiToFhirException extends Exception {
    private static final String TYPE = "EdiToFhir Exception";

    public EdiToFhirException() {
        super(TYPE);
    }

    public EdiToFhirException(String message) {
        super(message);
    }

    public EdiToFhirException(String message, Throwable cause) {
        super(message, cause);
    }
}
