package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.service.InvoiceVO;

import javax.annotation.Nonnull;

class InvoiceObjectConverter {
    @Nonnull
    static InvoiceVO convert(@Nonnull Invoice request) {
        var converted = new InvoiceVO(request.getInvoiceId());
        converted.setAmount(request.getAmount());
        converted.setBillToName(request.getBillToName());
        converted.setCompanyName(request.getCompanyName());
        converted.setName(request.getName());
        converted.setStatus(request.getStatus());
        return converted;
    }

    @Nonnull
    static Invoice convert(@Nonnull InvoiceVO from) {
        var converted = new Invoice();
        converted.setAmount(from.getAmount());
        converted.setInvoiceId(from.getInvoiceId());
        converted.setStatus(from.getStatus());
        converted.setName(from.getName());
        converted.setCompanyName(from.getCompanyName());
        converted.setBillToName(from.getBillToName());
        converted.setCreationDate(from.getCreationDate());
        converted.setLastModificationDate(from.getLastModificationDate());
        return converted;
    }
}
