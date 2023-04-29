package com.formlaez.application.model.response.form;

import com.formlaez.infrastructure.enumeration.FormSectionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormSectionResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private String variableName;

    private FormSectionType type;

    private boolean repeatable;
    private String repeatButtonLabel;
    private int position;

    private List<FormFieldResponse> fields;
}
