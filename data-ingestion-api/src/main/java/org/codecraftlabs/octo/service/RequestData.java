package org.codecraftlabs.octo.service;

import com.google.gson.Gson;

import java.time.Instant;

public final class RequestData<T> {
    private final T contents;
    private final long timestamp = Instant.now().toEpochMilli();
    private final RequestType requestType;

    public RequestData(T contents, RequestType requestType) {
        this.contents = contents;
        this.requestType = requestType;
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

    public String toJson() {
        var gson = new Gson();
        return gson.toJson(this);
    }
}
