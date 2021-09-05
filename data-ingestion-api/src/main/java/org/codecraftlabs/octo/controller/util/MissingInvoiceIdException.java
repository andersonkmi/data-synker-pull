package org.codecraftlabs.octo.controller.util;

public class MissingInvoiceIdException extends Exception {
    public MissingInvoiceIdException(String message) {
        super(message);
    }

    public MissingInvoiceIdException(String message, Throwable exception) {
        super(message, exception);
    }
}
