package org.codecraftlabs.octo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {
    private String invoiceId;
    private String name;
    private double amount;
    private String companyName;
    private String billToName;
    private String status;
    private long version;

    public void setInvoiceId(String invoiceId) {
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

    public void setVersion(long version) {
        this.version = version;
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
}
