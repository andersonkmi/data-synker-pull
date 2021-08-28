package org.codecraftlabs.octo.data;

import java.util.Objects;

public class Invoice {
    private final String invoiceId;
    private String name;
    private double amount;
    private String companyName;
    private String billToName;

    public Invoice(String invoiceId) {
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

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId);
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

        return Objects.equals(this.invoiceId, instance.invoiceId);
    }
}
