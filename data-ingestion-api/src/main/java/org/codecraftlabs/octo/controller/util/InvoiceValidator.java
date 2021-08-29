package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.BaseInvoice;
import org.codecraftlabs.octo.controller.MissingInvoiceIdException;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;

@Component
public class InvoiceValidator {
    public void validate(@CheckForNull BaseInvoice baseInvoice) throws MissingInvoiceIdException {
        if (baseInvoice == null) {
            throw new MissingInvoiceIdException("Invoice request is null");
        }

        if (baseInvoice.getInvoiceId() == null || baseInvoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }
    }
}
