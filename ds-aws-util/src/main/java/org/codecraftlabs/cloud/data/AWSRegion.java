package org.codecraftlabs.cloud.data;

import software.amazon.awssdk.regions.Region;

import javax.annotation.Nonnull;
import java.util.Optional;

public enum AWSRegion {
    AP_EAST_1("ap-east-1", "Asia Pacific (Hong Kong)", Region.AP_EAST_1),
    AP_SOUTH_1("ap-south-1", "Asia Pacific (Mumbai)", Region.AP_SOUTH_1),
    AP_NORTHEAST_1("ap-northeast-1", "Asia Pacific (Tokyo)", Region.AP_NORTHEAST_1),
    AP_NORTHEAST_2("ap-northeast-2", "Asia Pacific (Seoul)", Region.AP_NORTHEAST_2),
    AP_NORTHEAST_3("ap-northeast-3", "Asia Pacific (Osaka-Local)", Region.AP_NORTHEAST_3),
    AP_SOUTHEAST_1("ap-southeast-1", "Asia Pacific (Singapore)", Region.AP_SOUTHEAST_1),
    AP_SOUTHEAST_2("ap-southeast-2", "Asia Pacific (Sydney)", Region.AP_SOUTHEAST_2),
    EU_WEST_1("eu-west-1", "Europe (Ireland)", Region.EU_WEST_1),
    EU_WEST_2("eu-west-2", "Europe (London)", Region.EU_WEST_2),
    EU_WEST_3("eu-west-3", "Europe (Paris)", Region.EU_WEST_3),
    EU_NORTH_1("eu-north-1", "Europe (Stockholm)", Region.EU_NORTH_1),
    EU_CENTRAL_1("eu-central-1", "Europe (Frankfurt)", Region.EU_CENTRAL_1),
    ME_SOUTH_1("ap-south-1", "Middle East (Bahrain)", Region.ME_SOUTH_1),
    CA_CENTRAL_1("ca-central-1", "Canada (Central)", Region.CA_CENTRAL_1),
    SA_EAST_1("sa-east-1", "South America (Sao Paulo)", Region.SA_EAST_1),
    US_WEST_1(" us-west-1", "US West (N. California)", Region.US_WEST_1),
    US_WEST_2(" us-west-2", "US West (Oregon)", Region.US_WEST_2),
    US_EAST_2("us-east-2", "US East (Ohio)", Region.US_EAST_2),
    US_EAST_1("us-east-1", "US East (N. Virginia)", Region.US_EAST_1);

    private final String code;
    private final String description;
    private final Region region;

    AWSRegion(@Nonnull String code, @Nonnull String description, @Nonnull Region region) {
        this.code = code;
        this.description = description;
        this.region = region;
    }

    public String code() {
        return code;
    }

    public String description() {
        return description;
    }

    public Region region() {
        return region;
    }

    public static Optional<AWSRegion> findByCode(String code) {
        for (var region : values()) {
            if (region.code().equals(code)) {
                return Optional.of(region);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}