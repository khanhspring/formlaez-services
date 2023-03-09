package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.FormPageResponse;
import com.formlaez.infrastructure.model.entity.form.JpaFormPage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormPageResponseConvertor implements Converter<JpaFormPage, FormPageResponse> {

    private final FormSectionResponseConvertor formSectionResponseConvertor;

    @Nullable
    @Override
    public FormPageResponse convert(@Nullable JpaFormPage source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormPageResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .position(source.getPosition())
                .code(source.getCode())
                .sections(formSectionResponseConvertor.convert(source.getSections()))
                .build();
    }

    @Nullable
    public List<FormPageResponse> convert(@Nullable List<JpaFormPage> source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
