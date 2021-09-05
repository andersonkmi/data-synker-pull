package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceRequest;
import org.codecraftlabs.octo.controller.InvoiceUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public final class UpdateInvoiceValidator extends BaseInvoiceValidator {
    @Override
    public void validate(InvoiceRequest invoice) throws MissingInvoiceIdException, MissingVersionException, InvalidInvoiceStatusException {
        super.validate(invoice);

        var converted = (InvoiceUpdateRequest) invoice;
        if (converted.getVersion() == 0) {
            throw new MissingVersionException("Version not informed");
        }

        if (converted.getStatus() == null) {
            throw new InvalidInvoiceStatusException("Status not informed");
        }
    }
}
