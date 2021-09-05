package org.codecraftlabs.octo.controller;

public class InvoiceUpdateRequest extends InvoiceRequest {
    private long version;
    private String status;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
