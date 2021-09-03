package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvalidInvoiceStatusException;
import org.codecraftlabs.octo.controller.Invoice;
import org.codecraftlabs.octo.controller.MissingInvoiceIdException;
import org.codecraftlabs.octo.core.InvoiceStatus;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;

@Component
public final class InvoiceValidator extends BaseInvoiceValidator {

    @Override
    protected void validateInvoiceStatus(Invoice invoice) throws InvalidInvoiceStatusException {
        var status = InvoiceStatus.findByCode(invoice.getStatus());
        if (status.isEmpty()) {
            return;
        }

        if (status.get() != InvoiceStatus.CREATED) {
            throw new InvalidInvoiceStatusException("The invoice has an invalid status");
        }
    }
}
