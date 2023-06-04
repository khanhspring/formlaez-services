package com.formlaez.application.model.response.form;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldOptionResponse {
    private Long id;
    private String code;
    private String label;
    private String bgColor;

    private int position;
}
