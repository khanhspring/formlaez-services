package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTeamRequest {
    private String name;
    private String description;
    @NotNull
    private Long workspaceId;
}
