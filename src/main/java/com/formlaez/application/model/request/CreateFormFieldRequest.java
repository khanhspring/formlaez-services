package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateFormFieldRequest {

    private String sectionCode;
    private String title;
    private String description;
    private String placeholder;

    @NotBlank
    @Size(min = 21, max = 21)
    private String code;

    @NotBlank
    private String variableName;

    @NotNull
    private FormFieldType type;
    private boolean required;
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

    private List<@Valid FormFieldOptionRequest> options;

    @PositiveOrZero
    private int position;
}
