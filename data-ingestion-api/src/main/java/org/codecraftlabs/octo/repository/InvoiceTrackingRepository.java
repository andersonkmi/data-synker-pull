package org.codecraftlabs.octo.repository;

import javax.annotation.Nonnull;

public interface InvoiceTrackingRepository {
    long insert(@Nonnull InvoiceTracking invoiceTracking) throws InvoiceTrackingRepositoryException;
}
