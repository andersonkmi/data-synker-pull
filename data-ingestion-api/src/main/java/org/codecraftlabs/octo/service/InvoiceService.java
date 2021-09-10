package org.codecraftlabs.octo.service;

import org.codecraftlabs.octo.aws.AwsS3Service;
import org.codecraftlabs.octo.aws.S3Status;
import org.codecraftlabs.octo.repository.RepositoryException;
import org.codecraftlabs.octo.repository.postgres.InvoiceRepositoryPostgres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.codecraftlabs.octo.service.InvoiceObjectConverter.convert;
import static org.codecraftlabs.octo.service.RequestType.CREATE;
import static org.codecraftlabs.octo.service.RequestType.DELETE;
import static org.codecraftlabs.octo.service.RequestType.PATCH;
import static org.codecraftlabs.octo.service.RequestType.UPDATE;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    private InvoiceRepositoryPostgres invoiceRepositoryPostgres;

    private AwsS3Service awsS3Service;

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setInvoiceRepositoryPostgres(InvoiceRepositoryPostgres invoiceRepositoryPostgres) {
        this.invoiceRepositoryPostgres = invoiceRepositoryPostgres;
    }

    @Autowired
    public void setAwsS3Service(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    private boolean isS3UploadActive() {
        return Optional.ofNullable(environment.getProperty("octo.aws.s3.status")).orElse("").equalsIgnoreCase(S3Status.ENABLED.name());
    }

    public void insert(@Nonnull InvoiceVO invoiceVO) throws ServiceException {
        var converted = convert(invoiceVO, false);
        try {
            invoiceRepositoryPostgres.insert(converted);
            if (isS3UploadActive()) {
                var request = new RequestData<>(invoiceVO, CREATE);
                awsS3Service.saveRequest(request.toJson());
            }
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when inserting a new invoice record: '%s'", invoiceVO.getInvoiceId()), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    public void update(@Nonnull InvoiceVO invoice) throws ServiceException {
        var converted = convert(invoice, true);

        try {
            invoiceRepositoryPostgres.update(converted);
            if (isS3UploadActive()) {
                var request = new RequestData<>(invoice, UPDATE);
                awsS3Service.saveRequest(request.toJson());
            }
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when updating invoice: '%s'", invoice.getInvoiceId()), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    public void update(@Nonnull InvoicePatchVO invoice) throws ServiceException {
        var converted = convert(invoice);
        try {
            invoiceRepositoryPostgres.update(converted);
            if (isS3UploadActive()) {
                var request = new RequestData<>(invoice, PATCH);
                awsS3Service.saveRequest(request.toJson());
            }
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when updating invoice: '%s'", invoice.getInvoiceId()), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    public void delete(@Nonnull InvoiceVO invoice) throws ServiceException {
        logger.info(String.format("Deleting invoice: '%s'", invoice.getInvoiceId()));

        try {
            invoiceRepositoryPostgres.delete(invoice.getInvoiceId());
            if (isS3UploadActive()) {
                var request = new RequestData<>(invoice, DELETE);
                awsS3Service.saveRequest(request.toJson());
            }
        } catch (RepositoryException exception) {
            logger.error(String.format("Error when deleting invoice: '%s'", invoice.getInvoiceId()), exception);
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

    public Set<InvoiceVO> listAll() {
        var results = invoiceRepositoryPostgres.listAll();
        if (results.isEmpty()) {
            return Collections.emptySet();
        }
        return results.get().stream().map(InvoiceObjectConverter::convert).collect(Collectors.toSet());
    }

    public void delete() throws ServiceException {
        try {
            invoiceRepositoryPostgres.delete();
        } catch (RepositoryException exception) {
            logger.error("Error when deleting all invoices", exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    public Set<InvoiceVO> listInvoices(@Nonnull SortingField sortingField, @Nonnull SortingOrder sortingOrder, int pageSize, int page) {
        var results = invoiceRepositoryPostgres.listInvoices(sortingField.getCode(), sortingOrder.name(), pageSize, page);
        if (results.isEmpty()) {
            return Collections.emptySet();
        }
        return results.get().stream().map(InvoiceObjectConverter::convert).collect(Collectors.toSet());
    }
}
