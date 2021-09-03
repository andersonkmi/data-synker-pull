package org.codecraftlabs.octo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtendedInvoice extends Invoice {
    private Date creationDate;
    private Date lastModificationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
