package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateFormDocumentTemplateRequest {

    @NotBlank
    private String title;
    private String description;

    @NotNull
    private Long formId;

    @NotNull
    private MultipartFile file;
}
