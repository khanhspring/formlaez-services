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
@ConfigurationProperties(prefix = SendgridProperties.PREFIX)
public class SendgridProperties {
    public static final String PREFIX = "formlaez.sendgrid";

    @NotBlank
    private String apiKey;

    @NotBlank
    private String defaultSender;

    @NotBlank
    private String defaultSenderName;

    @NotBlank
    private String signUpTemplateId;

    @NotBlank
    private String welcomeTemplateId;

}
