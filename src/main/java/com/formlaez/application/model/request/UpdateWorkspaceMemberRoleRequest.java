package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateWorkspaceMemberRoleRequest {
    private Long workspaceId;
    private UUID userId;
    private WorkspaceMemberRole role;
}
