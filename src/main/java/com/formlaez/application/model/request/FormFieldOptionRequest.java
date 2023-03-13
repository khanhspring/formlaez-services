package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormFieldOptionRequest {

    @NotBlank
    private String code;
    @NotBlank
    private String label;
}