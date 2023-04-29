package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTeamMemberRoleRequest {
    private Long teamId;
    private String userId;
    private TeamMemberRole role;
}
