package org.codecraftlabs.cloud;


import org.codecraftlabs.cloud.data.AWSRegion;
import org.codecraftlabs.cloud.data.S3PutRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.annotation.Nonnull;
import java.util.Set;

import static software.amazon.awssdk.core.sync.RequestBody.fromString;

public final class S3Service {
    private final S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.builder().region(AWSRegion.US_EAST_1.region()).build();
    }

    public S3Service(@Nonnull AWSRegion awsRegion) {
        this.s3Client = S3Client.builder().region(awsRegion.region()).build();
    }

    public void putObject(@Nonnull S3PutRequest request) throws AWSException, InvalidPutRequestException {
        var bucket = request.bucket().orElseThrow(() -> new InvalidPutRequestException("Missing bucket"));
        var key = request.key().orElseThrow(() -> new InvalidPutRequestException("Missing key"));
        var contents = request.contents().orElseThrow(() -> new InvalidPutRequestException("Missing contents"));
        try {
            s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).build(), fromString(contents));
        } catch (S3Exception exception) {
            throw new AWSException("Error when calling S3 service", exception);
        }
    }

    public void putObjects(@Nonnull Set<S3PutRequest> requests) throws AWSException, InvalidPutRequestException {
        if (requests.isEmpty()) {
            return;
        }

        for (var item : requests) {
            putObject(item);
        }
    }
}
