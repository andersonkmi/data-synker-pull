package org.codecraftlabs.octo.repository;

import java.util.Date;

public class InvoiceTracking {
    private long id;
    private final String invoiceId;
    private final String status;
    private final Date creationDate;
    private final Date lastModificationDate;

    public InvoiceTracking(String invoiceId, String status, Date creationDate, Date lastModificationDate) {
        this.invoiceId = invoiceId;
        this.status = status;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
