package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceRequest;
import org.springframework.stereotype.Component;

@Component
public class InvoiceValidator {
    public void validate(InvoiceRequest invoice) throws MissingInvoiceIdException {
        if (invoice.getInvoiceId() == null || invoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }
    }
}
