package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormDocumentTemplateRequest {

    private Long id;
    @NotBlank
    private String title;
    private String description;
}
