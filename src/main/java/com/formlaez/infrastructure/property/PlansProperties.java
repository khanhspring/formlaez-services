package com.formlaez.infrastructure.property;

import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = PlansProperties.PREFIX)
public class PlansProperties {
    public static final String PREFIX = "formlaez.billing-plan";

    @NotEmpty
    private List<@Valid PlanInfo> plans;

    public PlanInfo getPlan(WorkspaceType code) {
        for (var plan : plans) {
            if (plan.getCode().equals(code)) {
                return plan;
            }
        }
        throw new InvalidParamsException("Plan is not supported");
    }

    @Getter
    @Setter
    @Validated
    public static class PlanInfo {

        @NotNull
        private WorkspaceType code;
        @NotNull
        private Long productId;
        @NotNull
        private BigDecimal price;
        @NotNull
        private int formLimit;
        @NotNull
        private int documentMergePerMonth;
        @NotNull
        private int submissionPerMonth;
        @NotNull
        private long fileStorageLimit;
        @NotNull
        private int workspaceMember;
    }
}
