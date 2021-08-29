package org.codecraftlabs.octo.core;

public enum InvoiceStatus {
    CREATED("created"),
    RECEIVED("received");

    private final String code;

    InvoiceStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
