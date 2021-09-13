package org.codecraftlabs.octo.service;

import com.google.gson.Gson;

import java.time.Instant;

public final class RequestData<T> {
    private final long timestamp = Instant.now().toEpochMilli();
    private final RequestType requestType;
    private final long invoiceTrackingId;
    private final T contents;

    public RequestData(T contents, RequestType requestType, long invoiceTrackingId) {
        this.contents = contents;
        this.requestType = requestType;
        this.invoiceTrackingId = invoiceTrackingId;
    }

    public T getContents() {
        return contents;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getInvoiceTrackingId() {
        return invoiceTrackingId;
    }

    public String toJson() {
        var gson = new Gson();
        return gson.toJson(this);
    }
}
