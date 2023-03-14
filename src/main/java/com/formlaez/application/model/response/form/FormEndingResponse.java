package com.formlaez.application.model.response.form;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormEndingResponse {
    private String content;
    private boolean hideButton;
}
