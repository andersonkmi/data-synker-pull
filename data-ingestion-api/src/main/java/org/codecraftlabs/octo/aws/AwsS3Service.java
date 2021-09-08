package org.codecraftlabs.octo.aws;

import com.google.gson.JsonObject;
import org.codecraftlabs.cloud.S3Service;
import org.codecraftlabs.cloud.data.AWSRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class AwsS3Service {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void saveRequest(@Nonnull JsonObject json) {
        var region = environment.getProperty("octo.aws.s3.region");
        var bucket = environment.getProperty("octo.aws.s3.bucket");
        var prefix = environment.getProperty("octo.aws.s3.prefix");

        var awsRegion = AWSRegion.findByCode(region);
        if (awsRegion.isPresent()) {
            var s3Service = new S3Service(awsRegion.get());
        }
    }
}
