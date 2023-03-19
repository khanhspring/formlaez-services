package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddWorkspaceMemberRequest {
    private Long workspaceId;
    @NotNull
    private UUID userId;
    @NotNull
    private WorkspaceMemberRole role;
}
