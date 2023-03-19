package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddTeamMemberRequest {
    private Long teamId;
    @NotNull
    private UUID userId;
    @NotNull
    private TeamMemberRole role;
}
