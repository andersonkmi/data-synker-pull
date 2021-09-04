package org.codecraftlabs.octo.controller;

import java.util.Collection;

public class ListResponse<T extends Collection<?>> {
    private final T records;

    public ListResponse(T records) {
        this.records = records;
    }

    public long getTotalCount() {
        if (records != null) {
            return records.size();
        }
        return 0;
    }

    public T getRecords() {
        return records;
    }
}
