package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BasicFormResponseConverter implements Converter<JpaForm, BasicFormResponse> {

    @Nullable
    @Override
    public BasicFormResponse convert(@Nullable JpaForm source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return BasicFormResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .code(source.getCode())
                .coverType(source.getCoverType())
                .coverColor(source.getCoverColor())
                .coverImageUrl(source.getCoverImageUrl())
                .status(source.getStatus())
                .sharingScope(source.getSharingScope())
                .acceptResponses(source.isAcceptResponses())
                .allowPrinting(source.isAllowPrinting())
                .allowResponseEditing(source.isAllowResponseEditing())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .build();
    }
}
