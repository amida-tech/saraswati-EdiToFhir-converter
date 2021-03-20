package com.amida.saraswati.edifhir.exception;

/**
 * Exception in stream service.
 *
 * @author warren
 */
public class StreamException extends Exception {

    public StreamException() {
        super("SteamException");
    }

    public StreamException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
