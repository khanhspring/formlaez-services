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
@ConfigurationProperties(prefix = AuthClientProperties.PREFIX)
public class AuthClientProperties {
    public static final String PREFIX = "formlaez.client.auth";

    @NotBlank
    private String baseUrl;
    @NotBlank
    private String authorization;
    @NotBlank
    private String redirectUri;
}
