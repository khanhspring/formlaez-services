package com.formlaez.infrastructure.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormFieldValueConverter {

    @Transactional(readOnly = true)
    public String asTextValue(@NonNull JpaFormField field, JsonNode rawValue, String defaultValue) {
        var value = asTextValue(field, rawValue);
        if (Objects.isNull(value)) {
            return defaultValue;
        }
        return value;
    }

    @Nullable
    @Transactional(readOnly = true)
    public String asTextValue(@NonNull JpaFormField field, JsonNode rawValue) {
        if (Objects.isNull(rawValue)) {
            return null;
        }
        if (rawValue.isNull()) {
            return null;
        }
        if (!field.getType().isFormControl()) {
            return null;
        }
        return switch (field.getType()) {
            case Dropdown -> getDropdownValue(field, rawValue);
            case MultipleChoice -> getMultiChoiceValue(field, rawValue);
            default -> rawValue.asText();
        };
    }

    private String getDropdownValue(JpaFormField field, JsonNode rawValue) {
        var options = field.getOptions();
        if (ObjectUtils.isEmpty(options)) {
            return null;
        }
        var selectedValue = rawValue.asText();
        for (var option : options) {
            if (option.getCode().equals(selectedValue)) {
                return option.getLabel();
            }
        }
        return null;
    }

    private String getMultiChoiceValue(JpaFormField field, JsonNode rawValue) {
        var options = field.getOptions();
        if (ObjectUtils.isEmpty(options)) {
            return null;
        }
        if (!(rawValue instanceof ArrayNode arrayValues)) {
            return null;
        }

        List<String> selectedValues = new ArrayList<>();
        for (var valueItem : arrayValues) {
            selectedValues.add(valueItem.asText());
        }
        List<String> results = new ArrayList<>();
        for (var option : options) {
            if (selectedValues.contains(option.getCode())) {
                results.add(option.getLabel());
            }
        }
        return String.join(", ", results);
    }
}