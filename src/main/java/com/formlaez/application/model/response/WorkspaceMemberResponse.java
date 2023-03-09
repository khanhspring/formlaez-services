package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMemberResponse {
    private UserResponse user;
    private WorkspaceMemberRole role;
}
