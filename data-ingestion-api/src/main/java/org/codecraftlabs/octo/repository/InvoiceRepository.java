package org.codecraftlabs.octo.repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InvoiceRepository {
    void insert(@Nonnull Invoice invoice) throws RepositoryException;

    Optional<Invoice> findByInvoiceId(@Nonnull String invoiceId) throws RepositoryException;

    Optional<Set<Invoice>> listAll() throws RepositoryException;

    void update(@Nonnull Invoice invoice) throws RepositoryException;
}
