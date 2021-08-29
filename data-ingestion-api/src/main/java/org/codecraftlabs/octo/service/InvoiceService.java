package org.codecraftlabs.octo.service;

import org.codecraftlabs.octo.repository.RepositoryException;
import org.codecraftlabs.octo.repository.postgres.InvoiceRepositoryPostgres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.codecraftlabs.octo.service.InvoiceObjectConverter.convert;

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

    public Optional<InvoiceVO> findByInvoiceId(@Nonnull String invoiceId) throws ServiceException {
        if (invoiceId.isBlank()) {
            return Optional.empty();
        }

        try {
            var invoice = invoiceRepositoryPostgres.findByInvoiceId(invoiceId);
            if (invoice.isEmpty()) {
                logger.info(String.format("No invoice was found with given invoiceId: '%s'", invoiceId));
                return Optional.empty();
            }

            return invoice.map(InvoiceObjectConverter::convert);
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when searching for an invoice with invoiceId: '%s'", invoiceId), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }
}
