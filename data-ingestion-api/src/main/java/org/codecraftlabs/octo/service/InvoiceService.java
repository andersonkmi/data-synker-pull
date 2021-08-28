package org.codecraftlabs.octo.service;

import org.codecraftlabs.octo.repository.Invoice;
import org.codecraftlabs.octo.repository.InvoiceRepositoryPostgres;
import org.codecraftlabs.octo.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Date;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepositoryPostgres invoiceRepositoryPostgres;

    public void insert(@Nonnull InvoiceVO invoiceVO) throws ServiceException {
        var converted = convert(invoiceVO, false);
        try {
            invoiceRepositoryPostgres.insert(converted);
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when inserting a new invoice record: '%s'", invoiceVO.getInvoiceId()), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    private Invoice convert(@Nonnull InvoiceVO from, boolean isUpdate) {
        var converted = new Invoice(from.getInvoiceId());
        converted.setAmount(from.getAmount());
        converted.setCompanyName(from.getCompanyName());
        converted.setBillToName(from.getBillToName());
        converted.setName(from.getName());
        if (!isUpdate) {
            converted.setCreationDate(new Date());
        }
        converted.setLastModificationDate(new Date());

        converted.setStatus(from.getStatus());
        return converted;
    }
}
