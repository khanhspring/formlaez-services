package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.FormSectionResponse;
import com.formlaez.infrastructure.model.entity.form.JpaFormSection;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormSectionResponseConverter implements Converter<JpaFormSection, FormSectionResponse> {

    private final FormFieldResponseConverter formFieldResponseConverter;

    @Nullable
    @Override
    public FormSectionResponse convert(@Nullable JpaFormSection source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormSectionResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .title(source.getTitle())
                .description(source.getDescription())
                .type(source.getType())
                .repeatable(source.isRepeatable())
                .repeatButtonLabel(source.getRepeatButtonLabel())
                .variableName(source.getVariableName())
                .position(source.getPosition())
                .fields(formFieldResponseConverter.convert(source.getFields()))
                .build();
    }

    @Nullable
    public List<FormSectionResponse> convert(@Nullable List<JpaFormSection> source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
