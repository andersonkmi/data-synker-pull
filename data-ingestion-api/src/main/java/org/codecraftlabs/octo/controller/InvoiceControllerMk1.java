package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.controller.util.InvoiceValidator;
import org.codecraftlabs.octo.controller.util.MissingInvoiceIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController("/v1")
public class InvoiceControllerMk1 {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceControllerMk1.class);

    @Autowired
    private InvoiceValidator invoiceValidator;

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceResponse> insert(@RequestBody InvoiceRequest invoice) {
        try {
            invoiceValidator.validate(invoice);
            return ResponseEntity.ok(new InvoiceResponse());
        } catch (MissingInvoiceIdException exception) {
            logger.error("Invoice does not have id.", exception);
            var response = new InvoiceResponse();
            response.setMessage("Missing invoice id.");
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }
}
