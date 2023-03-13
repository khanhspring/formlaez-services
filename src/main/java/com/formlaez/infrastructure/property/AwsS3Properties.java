package com.formlaez.infrastructure.property;

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
@ConfigurationProperties(prefix = AwsS3Properties.PREFIX)
public class AwsS3Properties {
    public static final String PREFIX = "formlaez.aws.s3";

    @NotNull
    private Credentials credentials;
    @NotBlank
    private String bucketName;

    @Getter
    @Setter
    @Validated
    public static class Credentials {

        @NotBlank
        private String accessKey;
        @NotBlank
        private String secretKey;
    }
}
