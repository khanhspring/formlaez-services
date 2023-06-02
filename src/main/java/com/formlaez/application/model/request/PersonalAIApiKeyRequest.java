package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalAIApiKeyRequest {
    @NotBlank
    private String model;
    @NotBlank
    private String apiKey;
}
