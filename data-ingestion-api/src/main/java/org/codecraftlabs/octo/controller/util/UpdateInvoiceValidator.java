package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceBaseRequest;
import org.codecraftlabs.octo.controller.InvoiceUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdateInvoiceValidator extends CreateInvoiceValidator implements InvoiceValidator {
    @Override
    public void validate(InvoiceBaseRequest invoice) throws MissingInvoiceIdException, MissingVersionException, InvalidInvoiceStatusException {
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
