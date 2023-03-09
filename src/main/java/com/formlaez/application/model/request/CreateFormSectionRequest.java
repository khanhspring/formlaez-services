package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormSectionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateFormSectionRequest {

    @NotNull
    private Long pageId;

    @NotBlank
    @Size(min = 21, max = 21)
    private String code;
    private String title;
    private String description;

    @NotBlank
    private String variableName;

    @NotNull
    private FormSectionType type;

    private boolean repeatable;
    private String repeatButtonLabel;

    @PositiveOrZero
    private int position;

    private List<@Valid CreateFormFieldRequest> fields;
}
