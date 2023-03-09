package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateFormFieldRequest {

    private String code;
    private String title;
    private String description;
    @NotBlank
    private String variableName;

    private boolean required;
    private String placeholder;
    private String content;
    private boolean hideTitle;
    private String url;
    private String caption;
    private String acceptedDomains;

    @PositiveOrZero
    private Integer minLength;

    @PositiveOrZero
    private Integer maxLength;

    private BigDecimal min;
    private BigDecimal max;

    private boolean readonly;
    private boolean multipleSelection;
    private boolean showTime;
}
