package org.codecraftlabs.octo.aws;

import org.codecraftlabs.cloud.AWSException;
import org.codecraftlabs.cloud.InvalidPutRequestException;
import org.codecraftlabs.cloud.S3Service;
import org.codecraftlabs.cloud.data.AWSRegion;
import org.codecraftlabs.cloud.data.S3PutRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AwsS3Service {
    private static final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void saveRequest(@Nonnull String json) {
        var region = environment.getProperty("octo.aws.s3.region");
        var bucket = environment.getProperty("octo.aws.s3.bucket");
        var prefix = environment.getProperty("octo.aws.s3.prefix");
        var keySalt = environment.getProperty("octo.aws.s3.key.salt");

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
            var s3Service = new S3Service(awsRegion.get());
            var putRequest = new S3PutRequest(bucket, getFullKeyName(prefix, keySalt), json);
            try {
                s3Service.putObject(putRequest);
            } catch (AWSException | InvalidPutRequestException exception) {
                logger.error(String.format("Error when sending object to S3: '%s'", exception.getMessage()), exception);
            }
        }
    }

    private String getFormattedDate() {
        var formatPattern = "yyyy-MM-dd-MM";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formatPattern);
        return dateFormatter.format(LocalDate.now());
    }

    private String getFullKeyName(@Nonnull String prefix, @Nonnull String keySalt) {
        var date = getFormattedDate();
        var timestamp = Instant.now().toEpochMilli();
        return prefix + "/" + date + "/" + timestamp + "_" + keySalt + ".json";
    }
}
