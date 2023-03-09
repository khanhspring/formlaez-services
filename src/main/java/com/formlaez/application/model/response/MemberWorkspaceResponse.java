package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberWorkspaceResponse {
    private WorkspaceResponse workspace;
    private WorkspaceMemberRole role;
    private Instant joinedDate;
}
