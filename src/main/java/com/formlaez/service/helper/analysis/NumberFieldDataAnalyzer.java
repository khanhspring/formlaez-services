package com.formlaez.service.helper.analysis;

import com.formlaez.application.model.response.form.analysis.NumberFieldAnalysisResponse;
import com.formlaez.application.model.response.form.analysis.ValueItemResponse;
import com.formlaez.infrastructure.converter.FormFieldResponseConverter;
import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NumberFieldDataAnalyzer implements FieldDataAnalyzer {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final FormFieldResponseConverter formFieldResponseConverter;

    @Override
    public NumberFieldAnalysisResponse analyze(JpaForm form, JpaFormField field) {
        var results = jpaFormSubmissionRepository.countValues(form.getId(), field.getCode());

        List<ValueItemResponse<BigDecimal>> values = results.stream()
                .filter(item -> Objects.nonNull(item.getValue()))
                .map(item -> ValueItemResponse.<BigDecimal>builder()
                        .value(new BigDecimal(item.getValue()))
                        .count(item.getCount())
                        .build())
                .sorted(Comparator.comparing(ValueItemResponse::getValue))
                .toList();

        var count = values.stream()
                .mapToLong(ValueItemResponse::getCount)
                .sum();

        var total = values.stream()
                .map(o -> o.getValue().multiply(BigDecimal.valueOf(o.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var avg = BigDecimal.ZERO;
        if (count != 0) {
            avg = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        }

        var min = values.stream()
                .min(Comparator.comparing(ValueItemResponse::getValue))
                .orElse(null);

        var max = values.stream()
                .max(Comparator.comparing(ValueItemResponse::getValue))
                .orElse(null);

        return NumberFieldAnalysisResponse.builder()
                .values(values)
                .count(count)
                .total(total)
                .avg(avg)
                .min(min)
                .max(max)
                .field(formFieldResponseConverter.convert(field))
                .build();
    }

    @Override
    public boolean isSupport(FormFieldType type) {
        return FormFieldType.InputNumber == type;
    }
}
