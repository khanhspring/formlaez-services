package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFormRequest {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private FormCoverType coverType;
    private String coverColor;
    private String coverImageUrl;

    @NotNull
    private FormScope scope;

    @NotNull
    private Long workspaceId;

    private Long teamId; // required when scope is Team
}
