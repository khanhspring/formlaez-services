package com.formlaez.infrastructure.property;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = FirebaseProperties.PREFIX)
public class FirebaseProperties {
    public static final String PREFIX = "formlaez.firebase";

    @NotNull
    private Resource serviceAccountJsonFile;

}
