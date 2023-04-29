package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddWorkspaceMemberRequest {
    private Long workspaceId;
    @NotNull
    private String userId;
    @NotNull
    private WorkspaceMemberRole role;
}
