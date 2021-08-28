package org.codecraftlabs.octo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    public void insert(@Nonnull InvoiceVO invoiceVO) throws ServiceException {
        //
    }
}
