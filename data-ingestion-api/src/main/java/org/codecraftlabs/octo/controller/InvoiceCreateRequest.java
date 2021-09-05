package org.codecraftlabs.octo.controller;

public class InvoiceCreateRequest extends InvoiceBaseRequest {
    private String name;
    private double amount;
    private String companyName;
    private String billToName;

    public final void setName(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setAmount(double amount) {
        this.amount = amount;
    }

    public final double getAmount() {
        return amount;
    }

    public final void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public final String getCompanyName() {
        return companyName;
    }

    public final void setBillToName(String billToName) {
        this.billToName = billToName;
    }

    public final String getBillToName() {
        return billToName;
    }

}
