package org.codecraftlabs.octo.core;

public enum PropertyKey {
    AWS_S3_BUCKET("aws.s3.bucket"),
    AWS_S3_KEY_SALT("aws.s3.key.salt"),
    AWS_S3_PREFIX("aws.s3.prefix"),
    AWS_S3_REGION("aws.s3.region"),
    AWS_S3_STATUS("aws.s3.status");

    private final String key;

    PropertyKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
