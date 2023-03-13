package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RemoveWorkspaceMemberRequest {
    private Long workspaceId;
    private UUID userId;
}