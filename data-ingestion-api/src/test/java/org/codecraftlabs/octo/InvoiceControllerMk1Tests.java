package org.codecraftlabs.octo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceControllerMk1Tests {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(0)
    public void createInvoice() throws Exception {
        var invoiceId = "invoice-" + new Date().getTime();
        this.mvc.perform(post("/v1/invoice")
                        .contentType(APPLICATION_JSON)
                        .content(createInvoice(invoiceId).toString())
                .accept(APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    public void findByInvoiceById() throws Exception {
        var invoiceId = "invoice-" + new Date().getTime();
        this.mvc.perform(post("/v1/invoice")
                .contentType(APPLICATION_JSON)
                .content(createInvoice(invoiceId).toString())
                .accept(APPLICATION_JSON)).andExpect(status().isCreated());

        this.mvc.perform(get("/v1/invoice/" + invoiceId)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void findByInvoiceByIdNotFound() throws Exception {
        this.mvc.perform(get("/v1/invoice/fake-id")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    public void findAll() throws Exception {
        this.mvc.perform(get("/v1/invoice").contentType(APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void update() throws Exception {
        var invoiceId = "invoice-" + new Date().getTime();
        this.mvc.perform(post("/v1/invoice")
                .contentType(APPLICATION_JSON)
                .content(createInvoice(invoiceId).toString())
                .accept(APPLICATION_JSON)).andExpect(status().isCreated());


    }


    private JSONObject createInvoice(String invoiceId) throws JSONException {
        JSONObject invoice = new JSONObject();
        invoice.put("invoiceId", invoiceId);
        invoice.put("amount", 123.34);
        invoice.put("billToName", "Johnny Cash");
        invoice.put("companyName", "Codecraft Labs");
        invoice.put("name", String.format("Invoice number: %s", invoiceId));
        return invoice;
    }
}
