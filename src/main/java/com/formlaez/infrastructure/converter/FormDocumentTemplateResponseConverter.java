package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import com.formlaez.infrastructure.model.entity.JpaFormDocumentTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormDocumentTemplateResponseConverter implements Converter<JpaFormDocumentTemplate, FormDocumentTemplateResponse> {

    @Nullable
    @Override
    public FormDocumentTemplateResponse convert(@Nullable JpaFormDocumentTemplate source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return FormDocumentTemplateResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .attachmentCode(source.getAttachment().getCode())
                .title(source.getTitle())
                .description(source.getDescription())
                .extension(source.getAttachment().getExtension())
                .size(source.getAttachment().getSize())
                .originalName(source.getAttachment().getOriginalName())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .build();
    }
}
