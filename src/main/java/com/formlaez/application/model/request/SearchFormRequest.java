package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormScope;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFormRequest {
    private String keyword;

    @NotNull
    private FormScope scope;

    private String createdBy;
    private Long teamId;
    @NotNull
    private Long workspaceId;
}
