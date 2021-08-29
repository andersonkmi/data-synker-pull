package org.codecraftlabs.octo.repository.postgres;

import org.codecraftlabs.octo.repository.Invoice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class InvoicePostgresRowMapper implements RowMapper<Invoice> {
    @Override
    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        var invoice = new Invoice();
        invoice.setId(rs.getLong("id"));
        invoice.setInvoiceId(rs.getString("invoiceid"));
        invoice.setName(rs.getString("invoicename"));
        invoice.setCompanyName(rs.getString("companyname"));
        invoice.setBillToName(rs.getString("billtoname"));
        invoice.setAmount(rs.getDouble("amount"));
        invoice.setStatus(rs.getString("status"));
        invoice.setCreationDate(rs.getTimestamp("creationdate"));
        invoice.setLastModificationDate(rs.getTimestamp("lastmodificationdate"));
        return invoice;
    }
}
