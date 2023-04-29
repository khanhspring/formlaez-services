package com.formlaez.application.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTeamMemberRequest {
    private Long teamId;
    private String userId;
}
