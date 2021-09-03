package org.codecraftlabs.octo.core;

import javax.annotation.Nonnull;
import java.util.Optional;

public enum InvoiceStatus {
    CREATED("created"),
    RECEIVED("received");

    private final String code;

    InvoiceStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static Optional<InvoiceStatus> findByCode(@Nonnull String code) {
        InvoiceStatus value = null;
        for (var item : values()) {
            if (item.code().equals(code)) {
                value = item;
                break;
            }
        }
        return Optional.ofNullable(value);
    }
}
