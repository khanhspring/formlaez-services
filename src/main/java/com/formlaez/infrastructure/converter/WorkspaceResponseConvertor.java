package com.formlaez.infrastructure.converter;

import com.formlaez.application.model.response.WorkspaceResponse;
import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkspaceResponseConvertor implements Converter<JpaWorkspace, WorkspaceResponse> {

    @Nullable
    @Override
    public WorkspaceResponse convert(@Nullable JpaWorkspace source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return WorkspaceResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .description(source.getDescription())
                .name(source.getName())
                .type(source.getType())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .build();
    }
}
