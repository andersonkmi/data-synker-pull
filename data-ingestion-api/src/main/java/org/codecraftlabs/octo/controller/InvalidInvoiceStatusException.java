package org.codecraftlabs.octo.controller;

public class InvalidInvoiceStatusException extends Exception {
    public InvalidInvoiceStatusException(String message) {
        super(message);
    }

    public InvalidInvoiceStatusException(String message, Throwable exception) {
        super(message, exception);
    }
}
