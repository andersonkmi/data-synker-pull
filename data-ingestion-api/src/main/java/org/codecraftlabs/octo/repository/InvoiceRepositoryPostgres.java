package org.codecraftlabs.octo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository("org.codecraftlabs.octo.repository.InvoiceRepositoryPostgres")
public class InvoiceRepositoryPostgres implements InvoiceRepository {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceRepositoryPostgres.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void insert(@Nonnull Invoice invoice) throws RepositoryException {
        try {
            var statement = "insert into invoice (invoiceid, invoicename, companyname, billtoname, amount, status, creationdate, lastmodificationdate) values (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(statement,
                    invoice.getInvoiceId(),
                    invoice.getName(),
                    invoice.getCompanyName(),
                    invoice.getBillToName(),
                    invoice.getAmount(),
                    invoice.getStatus(),
                    invoice.getCreationDate(),
                    invoice.getLastModificationDate());
            logger.info(String.format("Invoice inserted successfully: '%s'", invoice.getInvoiceId()));
        } catch (DuplicateKeyException exception) {
            throw new RepositoryException("Attempt to insert an invoice with the same invoice id", exception);
        } catch (DataAccessException exception) {
            throw new RepositoryException("Failed to insert a new invoice", exception);
        }
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public Optional<Invoice> findByInvoiceId(@Nonnull String invoiceId) throws RepositoryException {
        var statement = "select invoiceid, invoicename, companyname, billtoname, amount, status, creationdate, lastmodificationdate from invoice where invoiceid = ?";
        return Optional.empty();
    }
}
