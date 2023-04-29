package com.formlaez.application.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveWorkspaceMemberRequest {
    private Long workspaceId;
    private String userId;
}
