package com.formlaez.infrastructure.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = AuthInternalClientProperties.PREFIX)
public class AuthInternalClientProperties {
    public static final String PREFIX = "formlaez.client.auth-internal";

    @NotBlank
    private String baseUrl;
    @NotBlank
    private String authorization;
}
