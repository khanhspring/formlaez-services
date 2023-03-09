package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkspaceTypeRequest {
    private Long id;
    private WorkspaceType type;
}
