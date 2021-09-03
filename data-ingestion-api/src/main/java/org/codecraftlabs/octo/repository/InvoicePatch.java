package org.codecraftlabs.octo.repository;

import java.util.Date;

public class InvoicePatch {
    private final String invoiceId;
    private final long version;
    private String name;
    private String status;
    private Double amount;
    private final Date lastModificationDate = new Date();

    public InvoicePatch(String invoiceId, long version) {
        this.invoiceId = invoiceId;
        this.version = version;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public long getVersion() {
        return version;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
