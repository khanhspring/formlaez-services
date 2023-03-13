package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormSectionRequest {

    private String code;
    private String title;
    private String description;

    @NotBlank
    private String variableName;

    private String repeatButtonLabel;
}
