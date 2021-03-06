package org.codecraftlabs.octo.aws;

import org.codecraftlabs.cloud.AWSException;
import org.codecraftlabs.cloud.data.AWSRegion;
import org.codecraftlabs.cloud.s3.InvalidPutRequestException;
import org.codecraftlabs.cloud.s3.S3PutRequest;
import org.codecraftlabs.cloud.s3.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.codecraftlabs.cloud.data.ContentType.APPLICATION_JSON;
import static org.codecraftlabs.octo.core.PropertyKey.AWS_S3_BUCKET;
import static org.codecraftlabs.octo.core.PropertyKey.AWS_S3_KEY_SALT;
import static org.codecraftlabs.octo.core.PropertyKey.AWS_S3_PREFIX;
import static org.codecraftlabs.octo.core.PropertyKey.AWS_S3_REGION;

@Component
public class AwsS3Service {
    private static final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void saveRequest(@Nonnull String json) {
        var region = environment.getProperty(AWS_S3_REGION.key());
        var bucket = environment.getProperty(AWS_S3_BUCKET.key());
        var prefix = environment.getProperty(AWS_S3_PREFIX.key());
        var keySalt = environment.getProperty(AWS_S3_KEY_SALT.key());

        if (bucket == null || bucket.isBlank()) {
            logger.error("Bucket is not configured");
            return;
        }

        if (prefix == null || prefix.isBlank()) {
            logger.error("Prefix is not configured");
            return;
        }

        if (keySalt == null || keySalt.isBlank()) {
            logger.error("Key salt is not configured");
            return;
        }

        var awsRegion = AWSRegion.findByCode(region);
        if (awsRegion.isPresent()) {
            var s3Service = S3Service.builder().region(awsRegion.get()).build();
            var putRequest = new S3PutRequest(bucket, getFullKeyName(prefix, keySalt), json, APPLICATION_JSON);
            try {
                s3Service.putObject(putRequest);
            } catch (AWSException | InvalidPutRequestException exception) {
                logger.error(String.format("Error when sending object to S3: '%s'", exception.getMessage()), exception);
            }
        }
    }

    private String getFormattedDate() {
        var formatPattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formatPattern);
        return dateFormatter.format(LocalDate.now());
    }

    private String getFullKeyName(@Nonnull String prefix, @Nonnull String keySalt) {
        var date = getFormattedDate();
        var timestamp = Instant.now().toEpochMilli();
        return prefix + "/" + date + "/" + timestamp + "_" + keySalt + ".json";
    }
}
