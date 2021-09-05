package org.codecraftlabs.octo.controller.util;

public class MissingVersionException extends Exception {
    public MissingVersionException(String message) {
        super(message);
    }

    public MissingVersionException(String message, Throwable exception) {
        super(message, exception);
    }
}
