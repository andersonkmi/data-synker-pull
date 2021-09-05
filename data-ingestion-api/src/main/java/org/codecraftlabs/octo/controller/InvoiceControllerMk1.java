package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.controller.util.BaseInvoiceValidator;
import org.codecraftlabs.octo.controller.util.InvalidInvoiceStatusException;
import org.codecraftlabs.octo.controller.util.MissingInvoiceIdException;
import org.codecraftlabs.octo.controller.util.MissingVersionException;
import org.codecraftlabs.octo.controller.util.UpdateInvoiceValidator;
import org.codecraftlabs.octo.service.InvoiceService;
import org.codecraftlabs.octo.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static org.codecraftlabs.octo.controller.InvoiceObjectConverter.convert;
import static org.codecraftlabs.octo.controller.InvoiceObjectConverter.convertForInvoice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1")
public class InvoiceControllerMk1 {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceControllerMk1.class);

    private BaseInvoiceValidator baseInvoiceValidator;
    private InvoiceService invoiceService;
    private UpdateInvoiceValidator updateInvoiceValidator;

    @Autowired
    public void setBaseInvoiceValidator(BaseInvoiceValidator baseInvoiceValidator) {
        this.baseInvoiceValidator = baseInvoiceValidator;
    }

    @Autowired
    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Autowired
    public void setUpdateInvoiceValidator(UpdateInvoiceValidator updateInvoiceValidator) {
        this.updateInvoiceValidator = updateInvoiceValidator;
    }

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceResponse> insert(@RequestBody InvoiceRequest invoice) {
        try {
            // Validations
            baseInvoiceValidator.validate(invoice);

            // Conversion
            var invoiceVO = convertForInvoice(invoice);

            // Inserts invoice into the data repository
            invoiceService.insert(invoiceVO);

            // Returns response
            var response = new InvoiceResponse();
            response.setInvoiceId(invoice.getInvoiceId());
            response.setMessage("Invoice created");
            return ResponseEntity.status(CREATED).body(response);
        } catch (MissingInvoiceIdException | InvalidInvoiceStatusException | MissingVersionException exception) {
            logger.error("Invoice does not have id.", exception);
            var response = new InvoiceResponse();
            response.setMessage("Missing invoice id.");
            return ResponseEntity.status(BAD_REQUEST).body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to insert invoice", exception);
            var response = new InvoiceResponse();
            response.setInvoiceId(invoice.getInvoiceId());
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Invoice> findById(@PathVariable String invoiceId) {
        try {
            var invoice = invoiceService.findByInvoiceId(invoiceId);
            if (invoice.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(convert(invoice.get()));
        } catch (ServiceException exception) {
            logger.error("Failed to search invoice", exception);
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @GetMapping("/invoice")
    public ResponseEntity<ListResponse<Set<Invoice>>> listAll() {
        var results = invoiceService.listAll();
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        var converted = results.stream().map(InvoiceObjectConverter::convert).collect(Collectors.toSet());
        var response = new ListResponse<>(converted);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/invoice")
    public ResponseEntity<InvoiceResponse> update(@RequestBody InvoiceUpdateRequest invoice) {
        try {
            updateInvoiceValidator.validate(invoice);
            var converted = convertForInvoice(invoice);
            invoiceService.update(converted);

            var response = new InvoiceResponse();
            response.setInvoiceId(invoice.getInvoiceId());
            response.setMessage("Invoice updated");
            return ResponseEntity.ok().body(response);
        } catch (MissingInvoiceIdException exception) {
            logger.error("Invoice does not have id.", exception);
            var response = new InvoiceResponse();
            response.setMessage("Missing invoice id.");
            return ResponseEntity.status(BAD_REQUEST).body(response);
        } catch (MissingVersionException exception) {
            logger.error("Invoice does not have version.", exception);
            var response = new InvoiceResponse();
            response.setMessage("Missing invoice version.");
            return ResponseEntity.status(BAD_REQUEST).body(response);
        } catch (InvalidInvoiceStatusException exception) {
            logger.error("Invoice has an invalid status.", exception);
            var response = new InvoiceResponse();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to update invoice", exception);
            var response = new InvoiceResponse();
            response.setInvoiceId(invoice.getInvoiceId());
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/invoice/{invoiceId}")
    public ResponseEntity<InvoiceResponse> delete(@PathVariable String invoiceId) {
        try {
            invoiceService.delete(invoiceId);
            var response = new InvoiceResponse();
            response.setInvoiceId(invoiceId);
            response.setMessage("Invoice deleted");
            return ResponseEntity.ok().body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to delete invoice", exception);
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/invoice")
    public ResponseEntity<InvoiceResponse> delete() {
        try {
            invoiceService.delete();
            var response = new InvoiceResponse();
            response.setMessage("All invoices deleted");
            return ResponseEntity.ok().body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to delete all invoices", exception);
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @PatchMapping("/invoice")
    public ResponseEntity<InvoiceResponse> update(@RequestBody InvoicePatch invoicePatch) {
        var invoicePatchRequest = convert(invoicePatch);
        try {
            invoiceService.update(invoicePatchRequest);
            var response = new InvoiceResponse();
            response.setInvoiceId(invoicePatch.getInvoiceId());
            response.setMessage("Invoice updated");
            return ResponseEntity.ok().body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to updated invoice", exception);
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }
}
