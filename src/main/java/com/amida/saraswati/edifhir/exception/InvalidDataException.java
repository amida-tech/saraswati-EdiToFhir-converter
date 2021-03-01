package com.amida.saraswati.edifhir.exception;

public class InvalidDataException extends Exception {
    private static final String DEFAULT_MSG = "Invalid EDI data";

    public InvalidDataException() {
        super(DEFAULT_MSG);
    }

    public InvalidDataException(String msg) {
        super(msg);
    }

    public InvalidDataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
