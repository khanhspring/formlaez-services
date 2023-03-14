package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormEndingRequest {

    private Long formId;

    private String content;
    private boolean hideButton;
}
