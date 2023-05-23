package com.formlaez.application.model.response.form.ai;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIFormFieldOptionResponse {
    private String code;
    private String label;
}
