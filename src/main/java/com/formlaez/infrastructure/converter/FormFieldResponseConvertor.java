package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.FormFieldResponse;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FormFieldResponseConvertor implements Converter<JpaFormField, FormFieldResponse> {

    @Nullable
    @Override
    public FormFieldResponse convert(@Nullable JpaFormField source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormFieldResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .title(source.getTitle())
                .description(source.getDescription())
                .variableName(source.getVariableName())
                .caption(source.getCaption())
                .acceptedDomains(source.getAcceptedDomains())
                .content(source.getContent())
                .hideTitle(source.isHideTitle())
                .max(source.getMax())
                .min(source.getMin())
                .maxLength(source.getMaxLength())
                .minLength(source.getMinLength())
                .multipleSelection(source.isMultipleSelection())
                .placeholder(source.getPlaceholder())
                .url(source.getUrl())
                .type(source.getType())
                .position(source.getPosition())
                .readonly(source.isReadonly())
                .required(source.isRequired())
                .showTime(source.isShowTime())
                .build();
    }

    @Nullable
    public List<FormFieldResponse> convert(@Nullable List<JpaFormField> source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
