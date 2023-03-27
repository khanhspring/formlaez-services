package com.formlaez.application.model.response.form.analysis;

import com.formlaez.application.model.response.form.FormFieldResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumberFieldAnalysisResponse implements FormFieldAnalysisResponse {
    private FormFieldResponse field;
    private List<ValueItemResponse<BigDecimal>> values;
    private long count;
    private BigDecimal total;
    private BigDecimal avg;
    private ValueItemResponse<BigDecimal> min;
    private ValueItemResponse<BigDecimal> max;
}
