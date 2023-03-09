package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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

    @PositiveOrZero
    private int position;
}
