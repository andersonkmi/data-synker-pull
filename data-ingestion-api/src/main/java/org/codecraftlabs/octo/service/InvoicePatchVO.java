package org.codecraftlabs.octo.service;

public class InvoicePatchVO {
    private final String invoiceId;
    private String name;
    private double amount;
    private String status;
    private final long version;

    public InvoicePatchVO(String invoiceId, long version) {
        this.invoiceId = invoiceId;
        this.version = version;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public long getVersion() {
        return version;
    }
}
