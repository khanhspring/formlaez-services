package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTeamMemberRequest {
    private Long teamId;
    @NotNull
    private String userId;
    @NotNull
    private TeamMemberRole role;
}
