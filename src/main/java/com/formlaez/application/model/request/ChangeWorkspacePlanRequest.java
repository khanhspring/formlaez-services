package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeWorkspacePlanRequest {
    private Long workspaceId;
    @NotNull
    private WorkspaceType type;
}
