package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormResponseConvertor implements Converter<JpaForm, FormResponse> {

    private final FormPageResponseConvertor formPageResponseConvertor;

    @Nullable
    @Override
    public FormResponse convert(@Nullable JpaForm source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .code(source.getCode())
                .coverType(source.getCoverType())
                .coverColor(source.getCoverColor())
                .coverImageUrl(source.getCoverImageUrl())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .pages(formPageResponseConvertor.convert(source.getPages()))
                .build();
    }
}
