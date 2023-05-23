package com.formlaez.application.model.response.form.ai;

import com.formlaez.infrastructure.enumeration.FormFieldType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIFormFieldResponse {
    private String code;
    private String title;
    private FormFieldType type;

    private boolean required;
    private List<AIFormFieldOptionResponse> options;
}
