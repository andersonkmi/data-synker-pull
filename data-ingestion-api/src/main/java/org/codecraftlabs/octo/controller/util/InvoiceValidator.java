package org.codecraftlabs.octo.controller.util;

import org.codecraftlabs.octo.controller.InvoiceBaseRequest;

import javax.annotation.CheckForNull;

public interface InvoiceValidator {
    void validate(@CheckForNull InvoiceBaseRequest invoice) throws MissingInvoiceIdException, InvalidInvoiceStatusException, MissingVersionException;
}
