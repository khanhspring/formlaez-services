package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTeamMemberRoleRequest {
    private Long teamId;
    private UUID userId;
    private TeamMemberRole role;
}
