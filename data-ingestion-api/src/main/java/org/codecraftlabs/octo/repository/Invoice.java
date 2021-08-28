package org.codecraftlabs.octo.repository;

import java.util.Date;
import java.util.Objects;

public class Invoice {
    private long id;
    private final String invoiceId;
    private String name;
    private double amount;
    private String companyName;
    private String billToName;
    private String status;
    private Date creationDate;
    private Date lastModificationDate;

    public Invoice(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setBillToName(String billToName) {
        this.billToName = billToName;
    }

    public String getBillToName() {
        return billToName;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, id);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        Invoice instance = (Invoice) other;

        return Objects.equals(this.invoiceId, instance.invoiceId) && (id == instance.id);
    }
}
