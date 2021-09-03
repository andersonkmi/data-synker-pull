package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.Invoice;
import org.springframework.stereotype.Component;

@Component
public final class UpdateInvoiceValidator extends BaseInvoiceValidator {
    @Override
    protected void validateInvoiceStatus(Invoice invoice) {
        // It allows any status
    }
}
