package com.formlaez.service.helper.analysis;

import com.formlaez.application.model.response.form.analysis.NormalFieldAnalysisResponse;
import com.formlaez.application.model.response.form.analysis.ValueItemResponse;
import com.formlaez.infrastructure.converter.FormFieldResponseConverter;
import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArrayFieldDataAnalyzer implements FieldDataAnalyzer {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final FormFieldResponseConverter formFieldResponseConverter;

    @Override
    public NormalFieldAnalysisResponse analyze(JpaForm form, JpaFormField field) {
        var results = jpaFormSubmissionRepository.countArrayValues(form.getId(), field.getCode());

        List<ValueItemResponse<String>> values = results.stream()
                .map(item -> ValueItemResponse.<String>builder()
                        .value(item.getValue())
                        .count(item.getCount())
                        .build())
                .toList();

        var count = values.stream()
                .mapToLong(ValueItemResponse::getCount)
                .sum();

        return NormalFieldAnalysisResponse.builder()
                .values(values)
                .count(count)
                .field(formFieldResponseConverter.convert(field))
                .build();
    }

    @Override
    public boolean isSupport(FormFieldType type) {
        return List.of(
                FormFieldType.MultipleChoice,
                FormFieldType.StatusList
        ).contains(type);
    }
}
