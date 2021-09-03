package org.codecraftlabs.octo.service;

import org.codecraftlabs.octo.repository.Invoice;

import javax.annotation.Nonnull;
import java.util.Date;

class InvoiceObjectConverter {
    @Nonnull
    static InvoiceVO convert(@Nonnull Invoice from) {
        var converted = new InvoiceVO(from.getInvoiceId());
        converted.setName(from.getName());
        converted.setStatus(from.getStatus());
        converted.setCompanyName(from.getCompanyName());
        converted.setAmount(from.getAmount());
        converted.setBillToName(from.getBillToName());
        converted.setCreationDate(from.getCreationDate());
        converted.setLastModificationDate(from.getLastModificationDate());
        converted.setVersion(from.getVersion());
        return converted;
    }

    @Nonnull
    static Invoice convert(@Nonnull InvoiceVO from, boolean isUpdate) {
        var converted = new Invoice();
        converted.setInvoiceId(from.getInvoiceId());
        converted.setAmount(from.getAmount());
        converted.setCompanyName(from.getCompanyName());
        converted.setBillToName(from.getBillToName());
        converted.setName(from.getName());
        if (!isUpdate) {
            converted.setCreationDate(new Date());
        }
        converted.setLastModificationDate(new Date());
        converted.setVersion(from.getVersion());
        converted.setStatus(from.getStatus());
        return converted;
    }
}
