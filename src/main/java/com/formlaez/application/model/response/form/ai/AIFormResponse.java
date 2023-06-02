package com.formlaez.application.model.response.form.ai;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIFormResponse {
    private List<AIFormFieldResponse> fields;
}
