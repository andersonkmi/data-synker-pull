package org.codecraftlabs.octo.repository.postgres;

import org.codecraftlabs.octo.repository.InvoiceTracking;
import org.codecraftlabs.octo.repository.InvoiceTrackingRepository;
import org.codecraftlabs.octo.repository.InvoiceTrackingRepositoryException;
import org.codecraftlabs.octo.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import static org.codecraftlabs.octo.core.InvoiceTrackingStatus.CREATED;

@Repository("org.codecraftlabs.octo.repository.postgres.InvoiceTrackingRepositoryPostgres")
public class InvoiceTrackingRepositoryPostgres implements InvoiceTrackingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceTrackingRepositoryPostgres(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public long insert(@Nonnull InvoiceTracking invoiceTracking) throws InvoiceTrackingRepositoryException {
        try {
            var statementTracking = "insert into invoicetracking (invoiceid, status, creationdate, lastmodificationdate) values (?, ?, ?, ?) returning id";
            var keyHolder = new GeneratedKeyHolder();
            int total = jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(statementTracking, new String[] {"invoiceid", "status", "creationdate", "lastmodificationdate"});
                        ps.setString(1, invoiceTracking.getInvoiceId());
                        ps.setString(2, CREATED.name());
                        ps.setTimestamp(3, new Timestamp(invoiceTracking.getCreationDate().getTime()));
                        ps.setTimestamp(4, new Timestamp(invoiceTracking.getLastModificationDate().getTime()));
                        return ps;
                    },
                    keyHolder);

            var id = keyHolder.getKey();
            if (total == 0 || id == null) {
                throw new InvoiceTrackingRepositoryException("Invoice tracking insert has failed");
            }
            return keyHolder.getKey().longValue();
        } catch (DataAccessException exception) {
            throw new InvoiceTrackingRepositoryException("Failed to insert a new invoice", exception);
        }
    }
}
