package org.codecraftlabs.octo.service;

import java.util.Date;

public class InvoiceVO {
    private final String invoiceId;
    private String name;
    private double amount;
    private String companyName;
    private String billToName;
    private String status;
    private Date creationDate;
    private Date lastModificationDate;

    public InvoiceVO(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
}
