package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AIFormRequest {
    @NotNull
    private Long formId;
    @NotBlank
    private String message;
    private MultipartFile file;

    private String apiModel;
    private String apiKey;
}
