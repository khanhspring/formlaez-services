package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MergeDocumentFormSubmissionRequest {

    private String code;
    @NotNull
    private Long templateId;
}
