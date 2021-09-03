package org.codecraftlabs.octo.repository.postgres;

import org.codecraftlabs.octo.repository.Invoice;
import org.codecraftlabs.octo.repository.InvoiceRepository;
import org.codecraftlabs.octo.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("org.codecraftlabs.octo.repository.postgres.InvoiceRepositoryPostgres")
public class InvoiceRepositoryPostgres implements InvoiceRepository {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceRepositoryPostgres.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceRepositoryPostgres(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void insert(@Nonnull Invoice invoice) throws RepositoryException {
        try {
            var statement = "insert into invoice (invoiceid, invoicename, companyname, billtoname, amount, status, creationdate, lastmodificationdate, version) values (?, ?, ?, ?, ?, ?, ?, ?, 1)";
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
        try {
            var statement = "select id, invoiceid, invoicename, companyname, billtoname, amount, status, creationdate, lastmodificationdate, version from invoice where invoiceid = ?";
            var result = jdbcTemplate.queryForObject(statement, new InvoicePostgresRowMapper(), invoiceId);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException exception) {
            logger.info(String.format("No invoice found with provided id: '%s'", invoiceId));
            return Optional.empty();
        } catch (DataAccessException exception) {
            throw new RepositoryException("Failed to find an invoice by id", exception);
        }
    }

    @Override
    public Optional<Set<Invoice>> listAll() {
        var statement = "select id, invoiceid, invoicename, companyname, billtoname, amount, status, creationdate, lastmodificationdate, version from invoice order by invoiceid";
        var result = jdbcTemplate.query(statement, new InvoicePostgresRowMapper());
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new HashSet<>(result));
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void update(@Nonnull Invoice invoice) throws RepositoryException {
        long updatedVersion = invoice.getVersion() + 1;
        var statement = "update invoice set invoicename = ?, companyname = ?, billtoname = ?, amount = ?, status = ?, lastmodificationdate = ?, version = ? where invoiceid = ? and version = ?";

        try {
            int total = jdbcTemplate.update(statement, invoice.getName(), invoice.getCompanyName(), invoice.getBillToName(), invoice.getAmount(), invoice.getStatus(), invoice.getLastModificationDate(), updatedVersion, invoice.getInvoiceId(), invoice.getVersion());
            if (total == 0) {
                throw new RepositoryException("The invoice was not updated since it was changed by other process in the meantime");
            }
        } catch (DataAccessException exception) {
            throw new RepositoryException("Failed to update invoice", exception);
        }
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void delete(@Nonnull String invoiceId) throws RepositoryException {
        var statement = "delete from invoice where invoiceid = ?";
        try {
            int total = jdbcTemplate.update(statement, invoiceId);
            if (total == 0) {
                throw new RepositoryException("The invoice was not deleted since it was deleted by other process in the meantime");
            }
        } catch (DataAccessException exception) {
            throw new RepositoryException("Failed to delete invoice", exception);
        }
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void delete() throws RepositoryException {
        var statement = "delete from invoice";
        try {
            int total = jdbcTemplate.update(statement);
            logger.info(String.format("Total invoices deleted: %d", total));
        } catch (DataAccessException exception) {
            throw new RepositoryException("Failed to delete all invoices", exception);
        }
    }
}
