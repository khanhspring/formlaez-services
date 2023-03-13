package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormSharingScope;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormSettingsRequest {
    private Long id;

    @NotNull
    private FormSharingScope sharingScope;
    private boolean acceptResponses;
    private boolean allowPrinting;
    private boolean allowResponseEditing;
}
