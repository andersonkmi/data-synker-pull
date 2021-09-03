package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.service.InvoiceVO;

import javax.annotation.Nonnull;

import static org.codecraftlabs.octo.core.InvoiceStatus.CREATED;

class InvoiceObjectConverter {
    @Nonnull
    static InvoiceVO convertForInvoice(@Nonnull Invoice request) {
        var converted = new InvoiceVO(request.getInvoiceId());
        converted.setAmount(request.getAmount());
        converted.setBillToName(request.getBillToName());
        converted.setCompanyName(request.getCompanyName());
        converted.setName(request.getName());
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            converted.setStatus(CREATED.code());
        } else {
            converted.setStatus(request.getStatus());
        }
        converted.setVersion(request.getVersion());
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
}
