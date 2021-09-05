package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceRequest;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;

@Component
public class BaseInvoiceValidator {
    public void validate(@CheckForNull InvoiceRequest invoice) throws MissingInvoiceIdException, InvalidInvoiceStatusException, MissingVersionException {
        if (invoice == null) {
            throw new MissingInvoiceIdException("Invoice request is null");
        }

        if (invoice.getInvoiceId() == null || invoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }
    }
}
