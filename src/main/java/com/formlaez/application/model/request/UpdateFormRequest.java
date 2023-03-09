package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormRequest {
    private Long id;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private FormCoverType coverType;
    private String coverColor;
    private String coverImageUrl;
}
