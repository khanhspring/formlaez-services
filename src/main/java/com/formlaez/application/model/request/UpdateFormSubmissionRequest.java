package com.formlaez.application.model.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormSubmissionRequest {

    private String code;
    private JsonNode data;
}
