package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFormDocumentTemplateRequest {
    private String keyword;

    @NotNull
    private Long formId;
}
