package com.formlaez.service.helper.analysis;

import com.formlaez.application.model.response.form.analysis.DateTimeFieldAnalysisResponse;
import com.formlaez.application.model.response.form.analysis.ValueItemResponse;
import com.formlaez.infrastructure.converter.FormFieldResponseConverter;
import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DateTimeFieldDataAnalyzer implements FieldDataAnalyzer {

    private static final String DATE_TIME = "YYYY-MM-DD HH:mm";
    private static final String DATE_ONLY = "YYYY-MM-DD";
    private static final String YEAR_ONLY = "YYYY";

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final FormFieldResponseConverter formFieldResponseConverter;

    @Override
    public DateTimeFieldAnalysisResponse analyze(JpaForm form, JpaFormField field) {
        List<ValueItemResponse<String>> dateTimes = null;
        if (field.isShowTime()) {
            dateTimes = countDateValues(form, field, DATE_TIME);
        }
        List<ValueItemResponse<String>> dates = countDateValues(form, field, DATE_ONLY);
        List<ValueItemResponse<String>> years = countDateValues(form, field, YEAR_ONLY);

        long count = jpaFormSubmissionRepository.count(form.getId(), field.getCode());

        return DateTimeFieldAnalysisResponse.builder()
                .count(count)
                .dateTimes(dateTimes)
                .dates(dates)
                .years(years)
                .field(formFieldResponseConverter.convert(field))
                .build();
    }

    private List<ValueItemResponse<String>> countDateValues(JpaForm form, JpaFormField field, String dateFormat) {
        var dateValues = jpaFormSubmissionRepository.countDateValues(form.getId(), field.getCode(), dateFormat);

        return dateValues.stream()
                .filter(item -> Objects.nonNull(item.getValue()))
                .map(item -> ValueItemResponse.<String>builder()
                        .value(item.getValue())
                        .count(item.getCount())
                        .build())
                .sorted(Comparator.comparing(ValueItemResponse::getValue))
                .toList();
    }

    @Override
    public boolean isSupport(FormFieldType type) {
        return FormFieldType.Datetime == type;
    }
}
