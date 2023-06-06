package com.formlaez.infrastructure.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.infrastructure.converter.DataJsonConverter.Options;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PageViewDataConverter implements Converter<JpaFormSubmission, JsonNode> {

    private final DataJsonConverter dataJsonConverter;

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public JsonNode convert(@Nullable JpaFormSubmission source) {
        return dataJsonConverter.convert(
                source,
                Options.builder()
                        .useCodeAsKey(true)
                        .keepSelectionData(true)
                        .build()
        );
    }

}
