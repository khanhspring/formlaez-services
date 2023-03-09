package com.formlaez.application.model.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFormSubmissionRequest {

    private String formCode;
    private JsonNode data;
}
