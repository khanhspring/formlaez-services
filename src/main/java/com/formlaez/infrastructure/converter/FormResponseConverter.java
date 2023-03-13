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
public class FormResponseConverter implements Converter<JpaForm, FormResponse> {

    private final FormPageResponseConverter formPageResponseConverter;

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
                .status(source.getStatus())
                .sharingScope(source.getSharingScope())
                .acceptResponses(source.isAcceptResponses())
                .allowPrinting(source.isAllowPrinting())
                .allowResponseEditing(source.isAllowResponseEditing())
                .lastModifiedDate(source.getLastModifiedDate())
                .pages(formPageResponseConverter.convert(source.getPages()))
                .build();
    }
}
