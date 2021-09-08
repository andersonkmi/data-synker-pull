package org.codecraftlabs.cloud;

public class InvalidPutRequestException extends Exception {
    public InvalidPutRequestException(String message) {
        super(message);
    }

    public InvalidPutRequestException(String message, Throwable exception) {
        super(message, exception);
    }
}
