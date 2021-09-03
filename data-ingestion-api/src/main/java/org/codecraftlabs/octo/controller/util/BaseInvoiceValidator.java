package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvalidInvoiceStatusException;
import org.codecraftlabs.octo.controller.Invoice;
import org.codecraftlabs.octo.controller.MissingInvoiceIdException;

import javax.annotation.CheckForNull;

public abstract class BaseInvoiceValidator {
    public final void validate(@CheckForNull Invoice invoice) throws MissingInvoiceIdException, InvalidInvoiceStatusException {
        if (invoice == null) {
            throw new MissingInvoiceIdException("Invoice request is null");
        }

        if (invoice.getInvoiceId() == null || invoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }

        validateInvoiceStatus(invoice);
    }

    abstract protected void validateInvoiceStatus(Invoice invoice) throws InvalidInvoiceStatusException;
}
