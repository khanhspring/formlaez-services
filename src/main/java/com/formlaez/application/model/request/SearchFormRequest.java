package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.FormScope;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class SearchFormRequest {
    private String keyword;

    @NotNull
    private FormScope scope;

    private UUID createdBy;
    private Long teamId;
}
