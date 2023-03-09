package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkspaceRequest {
    private Long id;
    private String name;
    private String description;
}
