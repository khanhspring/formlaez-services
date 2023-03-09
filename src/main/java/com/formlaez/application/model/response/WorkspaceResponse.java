package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private WorkspaceType type;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
