package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberResponse {
    private UserResponse user;
    private TeamMemberRole role;
    private Instant joinedDate;
}
