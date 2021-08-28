package org.codecraftlabs.octo.service;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable exception) {
        super(message, exception);
    }
}
