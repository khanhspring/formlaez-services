package com.formlaez.application.model.response.form.analysis;

import com.formlaez.application.model.response.form.FormFieldResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateTimeFieldAnalysisResponse implements FormFieldAnalysisResponse {
    private FormFieldResponse field;
    private List<ValueItemResponse<String>> years;
    private List<ValueItemResponse<String>> dates;
    private List<ValueItemResponse<String>> dateTimes;
    private long count;
}
