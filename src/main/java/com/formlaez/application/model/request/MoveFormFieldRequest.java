package com.formlaez.application.model.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveFormFieldRequest {

    private String fieldCode;
    @PositiveOrZero
    private int newPosition;
}
