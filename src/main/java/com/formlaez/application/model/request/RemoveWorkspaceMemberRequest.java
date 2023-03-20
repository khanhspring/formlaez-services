package com.formlaez.application.model.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveWorkspaceMemberRequest {
    private Long workspaceId;
    private UUID userId;
}
