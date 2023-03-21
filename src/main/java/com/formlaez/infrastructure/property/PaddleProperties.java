package com.formlaez.infrastructure.property;

import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import jakarta.validation.constraints.NotBlank;
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
@ConfigurationProperties(prefix = PaddleProperties.PREFIX)
public class PaddleProperties {
    public static final String PREFIX = "formlaez.paddle";

    @NotNull
    private Resource publicKey;

    @NotBlank
    private String plusPlanId;
    @NotBlank
    private String businessPlanId;

    @NotNull
    private Long vendorId;
    @NotBlank
    private String vendorAuthCode;
    @NotBlank
    private String apiBaseUrl;

    public WorkspaceType getWorkspaceType(String id) {
        if (plusPlanId.equals(id)) {
            return WorkspaceType.Plus;
        }
        if (businessPlanId.equals(id)) {
            return WorkspaceType.Business;
        }
        throw new InvalidParamsException("Workspace type is not supported");
    }

    public String getPlanId(WorkspaceType type) {
        if (type == WorkspaceType.Plus) {
            return plusPlanId;
        }
        if (type == WorkspaceType.Business) {
            return businessPlanId;
        }
        throw new InvalidParamsException("Workspace type is not supported");
    }
}
