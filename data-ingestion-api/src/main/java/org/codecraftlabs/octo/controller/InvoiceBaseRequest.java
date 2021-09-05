package org.codecraftlabs.octo.controller;

public class InvoiceBaseRequest {
    private String invoiceId;

    public final void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public final String getInvoiceId() {
        return invoiceId;
    }
}
