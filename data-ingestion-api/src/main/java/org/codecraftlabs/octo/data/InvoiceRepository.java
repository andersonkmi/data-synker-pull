package org.codecraftlabs.octo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Repository
public class InvoiceRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert(@Nonnull Invoice invoice) {

    }
}
