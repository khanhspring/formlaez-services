package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkspaceMemberRoleRequest {
    private Long workspaceId;
    private String userId;
    private WorkspaceMemberRole role;
}
