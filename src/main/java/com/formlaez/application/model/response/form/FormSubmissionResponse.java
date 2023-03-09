package com.formlaez.application.model.response.form;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.application.model.response.UserResponse;
import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormSubmissionResponse {
    private Long id;
    private String code;
    private JsonNode data;
    private Instant createdDate;
    private UserResponse createdBy;
    private Instant lastModifiedDate;
    private UserResponse lastModifiedBy;
}
