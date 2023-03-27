package com.formlaez.application.model.response.form.analysis;

import com.formlaez.application.model.response.form.FormFieldResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalFieldAnalysisResponse implements FormFieldAnalysisResponse {
    private FormFieldResponse field;
    private List<ValueItemResponse<String>> values;
    private long count;
}
