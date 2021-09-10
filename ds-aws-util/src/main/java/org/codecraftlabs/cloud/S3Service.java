package org.codecraftlabs.cloud;

import org.codecraftlabs.cloud.data.S3PutRequest;

import javax.annotation.Nonnull;
import java.util.Set;

public interface S3Service {
    
    static S3ServiceBuilder builder() {
        return new DefaultS3ServiceBuilder();
    }
    
    void putObject(@Nonnull S3PutRequest request) throws AWSException, InvalidPutRequestException;

    void putObjects(@Nonnull Set<S3PutRequest> requests) throws AWSException, InvalidPutRequestException;
}
