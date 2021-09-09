package org.codecraftlabs.cloud.data;

public enum ContentType {
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN("text/plain");

    private final String code;

    ContentType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
