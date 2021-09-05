package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceBaseRequest;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;

@Component
public class CreateInvoiceValidator implements InvoiceValidator {
    public void validate(@CheckForNull InvoiceBaseRequest invoice) throws MissingInvoiceIdException, InvalidInvoiceStatusException, MissingVersionException {
        if (invoice == null) {
            throw new MissingInvoiceIdException("Invoice request is null");
        }

        if (invoice.getInvoiceId() == null || invoice.getInvoiceId().isBlank()) {
            throw new MissingInvoiceIdException("Invoice does not have mandatory id");
        }
    }
}
