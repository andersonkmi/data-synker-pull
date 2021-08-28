package org.codecraftlabs.octo.repository;

import javax.annotation.Nonnull;

public interface InvoiceRepository {
    void insert(@Nonnull Invoice invoice) throws RepositoryException;
}
