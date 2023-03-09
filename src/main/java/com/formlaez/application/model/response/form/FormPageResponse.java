package com.formlaez.application.model.response.form;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormPageResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private int position;

    private List<FormSectionResponse> sections;
}
