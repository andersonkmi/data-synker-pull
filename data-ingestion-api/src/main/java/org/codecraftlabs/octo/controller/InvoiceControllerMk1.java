package org.codecraftlabs.octo.controller;

import org.codecraftlabs.octo.controller.util.InvoiceValidator;
import org.codecraftlabs.octo.controller.util.MissingInvoiceIdException;
import org.codecraftlabs.octo.service.InvoiceService;
import org.codecraftlabs.octo.service.InvoiceVO;
import org.codecraftlabs.octo.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

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
    public ResponseEntity<InvoiceResponse> insert(@RequestBody InvoiceRequest invoice) {
        try {
            // Validations
            invoiceValidator.validate(invoice);

            // Conversion
            var invoiceVO = convert(invoice);

            // Inserts invoice into the data repository
            invoiceService.insert(invoiceVO);

            // Returns response
            var response = new InvoiceResponse();
            response.setInvoiceId(invoice.getInvoiceId());
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
            response.setInvoiceId(invoice.getInvoiceId());
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }

    @Nonnull
    private InvoiceVO convert(@Nonnull InvoiceRequest request) {
        var converted = new InvoiceVO(request.getInvoiceId());
        converted.setAmount(request.getAmount());
        converted.setBillToName(request.getBillToName());
        converted.setCompanyName(request.getCompanyName());
        converted.setName(request.getName());
        converted.setStatus(request.getStatus());
        return converted;
    }
}
