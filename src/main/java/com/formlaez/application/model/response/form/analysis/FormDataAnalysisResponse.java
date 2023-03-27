package com.formlaez.application.model.response.form.analysis;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormDataAnalysisResponse {
    private List<FormFieldAnalysisResponse> items;
    private long count;
}
