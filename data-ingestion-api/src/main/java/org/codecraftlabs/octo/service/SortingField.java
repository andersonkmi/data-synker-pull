package org.codecraftlabs.octo.service;

import java.util.Optional;

public enum SortingField {
    ID("id"),
    INVOICE_ID("invoiceId"),
    NAME("name"),
    COMPANY_NAME("companyName"),
    BILL_TO_NAME("billToName"),
    AMOUNT("amount"),
    CREATION_DATE("creationDate"),
    NONE("none");

    private final String code;

    SortingField(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<SortingField> findByCode(String code) {
        for(var item : values()) {
            if (item.getCode().equals(code)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
