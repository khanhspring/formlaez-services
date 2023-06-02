package com.formlaez.infrastructure.property.aws;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = AwsProperties.PREFIX)
public class AwsProperties {
    public static final String PREFIX = "formlaez.aws";

    @NotNull
    private Credentials credentials;

    @NotNull
    private AwsS3Properties s3;

    @Getter
    @Setter
    @Validated
    public static class Credentials {

        @NotBlank
        private String accessKey;
        @NotBlank
        private String secretKey;
    }

    @Getter
    @Setter
    @Validated
    public static class AwsS3Properties {
        @NotBlank
        private String bucketName;
    }


    public AwsS3Properties s3() {
        return s3;
    }

}
