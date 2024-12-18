package com.formlaez.infrastructure.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.formlaez.infrastructure.enumeration.FormSectionType;
import com.formlaez.infrastructure.model.entity.form.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormSubmissionDataJsonConverter implements Converter<JpaFormSubmission, JsonNode> {

    private final DataJsonConverter dataJsonConverter;

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public JsonNode convert(@Nullable JpaFormSubmission source) {
       return dataJsonConverter.convert(source);
    }

}
