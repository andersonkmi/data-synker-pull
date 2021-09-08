package org.codecraftlabs.cloud.data;

import java.util.Objects;
import java.util.Optional;

public class S3PutRequest {
    private final String bucket;
    private final String key;
    private final String contents;

    public S3PutRequest(String bucket, String key, String contents) {
        this.bucket = bucket;
        this.key = key;
        this.contents = contents;
    }

    public Optional<String> bucket() {
        return Optional.ofNullable(bucket);
    }

    public Optional<String> key() {
        return Optional.ofNullable(key);
    }

    public Optional<String> contents() {
        return Optional.ofNullable(contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, key, contents);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!getClass().equals(other.getClass())) {
            return false;
        }

        var instance = (S3PutRequest) other;

        return Objects.equals(bucket, instance.bucket) &&
               Objects.equals(key, instance.key) &&
               Objects.equals(contents, instance.contents);
    }
}
