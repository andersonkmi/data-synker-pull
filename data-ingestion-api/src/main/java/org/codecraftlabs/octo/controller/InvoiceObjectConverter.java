package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.service.InvoicePatchVO;
import org.codecraftlabs.octo.service.InvoiceVO;

import javax.annotation.Nonnull;

import static org.codecraftlabs.octo.core.InvoiceStatus.CREATED;

class InvoiceObjectConverter {
    private static final int INITIAL_VERSION = 1;
    @Nonnull
    static InvoiceVO convertForInvoice(@Nonnull InvoiceCreateRequest request) {
        var converted = new InvoiceVO(request.getInvoiceId());
        converted.setAmount(request.getAmount());
        converted.setBillToName(request.getBillToName());
        converted.setCompanyName(request.getCompanyName());
        converted.setName(request.getName());
        converted.setStatus(CREATED.code());
        converted.setVersion(INITIAL_VERSION);
        return converted;
    }

    @Nonnull
    static InvoiceVO convertForInvoice(@Nonnull InvoiceUpdateRequest request) {
        var converted = new InvoiceVO(request.getInvoiceId());
        converted.setAmount(request.getAmount());
        converted.setBillToName(request.getBillToName());
        converted.setCompanyName(request.getCompanyName());
        converted.setName(request.getName());
        converted.setVersion(request.getVersion());
        converted.setStatus(request.getStatus());
        return converted;
    }

    @Nonnull
    static Invoice convert(@Nonnull InvoiceVO from) {
        var converted = new ExtendedInvoice();
        converted.setAmount(from.getAmount());
        converted.setInvoiceId(from.getInvoiceId());
        converted.setStatus(from.getStatus());
        converted.setName(from.getName());
        converted.setCompanyName(from.getCompanyName());
        converted.setBillToName(from.getBillToName());
        converted.setCreationDate(from.getCreationDate());
        converted.setLastModificationDate(from.getLastModificationDate());
        converted.setVersion(from.getVersion());
        return converted;
    }

    @Nonnull
    static InvoicePatchVO convert(@Nonnull InvoicePatchRequest from) {
        var converted = new InvoicePatchVO(from.getInvoiceId(), from.getVersion());
        converted.setAmount(from.getAmount());
        converted.setName(from.getName());
        converted.setStatus(from.getStatus());
        return converted;
    }
}
