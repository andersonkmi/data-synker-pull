package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.Invoice;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;

@Component
public class InvoiceValidator {
    public void validate(@CheckForNull Invoice invoice) throws MissingInvoiceIdException {
        if (invoice == null) {
            throw new MissingInvoiceIdException("Invoice request is null");
        }

        if (invoice.getInvoiceId() == null || invoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }
    }
}
