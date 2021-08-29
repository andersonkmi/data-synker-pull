package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.controller.util.InvoiceValidator;
import org.codecraftlabs.octo.service.InvoiceService;
import org.codecraftlabs.octo.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.codecraftlabs.octo.controller.InvoiceObjectConverter.convert;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1")
public class InvoiceControllerMk1 {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceControllerMk1.class);

    @Autowired
    private InvoiceValidator invoiceValidator;

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceResponse> insert(@RequestBody BaseInvoice baseInvoice) {
        try {
            // Validations
            invoiceValidator.validate(baseInvoice);

            // Conversion
            var invoiceVO = InvoiceObjectConverter.convertForInvoiceCreation(baseInvoice);

            // Inserts invoice into the data repository
            invoiceService.insert(invoiceVO);

            // Returns response
            var response = new InvoiceResponse();
            response.setInvoiceId(baseInvoice.getInvoiceId());
            response.setMessage("Invoice created");
            return ResponseEntity.status(CREATED).body(response);
        } catch (MissingInvoiceIdException exception) {
            logger.error("Invoice does not have id.", exception);
            var response = new InvoiceResponse();
            response.setMessage("Missing invoice id.");
            return ResponseEntity.status(BAD_REQUEST).body(response);
        } catch (ServiceException exception) {
            logger.error("Failed to insert invoice", exception);
            var response = new InvoiceResponse();
            response.setInvoiceId(baseInvoice.getInvoiceId());
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<BaseInvoice> findById(@PathVariable String invoiceId) {
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
}
