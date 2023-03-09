package com.formlaez.infrastructure.repository.custom;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.infrastructure.model.entity.JpaUser;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JpaFormSubmissionSearchResult {
    private Long id;
    private String code;
    private JsonNode data;
    private JpaUser createdBy;
    private Instant createdDate;
    private JpaUser lastModifiedBy;
    private Instant lastModifiedDate;
}
