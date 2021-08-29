package org.codecraftlabs.octo.repository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface InvoiceRepository {
    void insert(@Nonnull Invoice invoice) throws RepositoryException;

    Optional<Invoice> findByInvoiceId(@Nonnull String invoiceId) throws RepositoryException;
}
