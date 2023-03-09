package com.formlaez.application.model.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveFormSectionRequest {

    private String sectionCode;
    @PositiveOrZero
    private int newPosition;
}
