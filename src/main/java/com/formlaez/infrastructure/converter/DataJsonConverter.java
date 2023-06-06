package com.formlaez.infrastructure.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.enumeration.FormSectionType;
import com.formlaez.infrastructure.model.entity.form.*;
import lombok.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.formlaez.infrastructure.enumeration.FormFieldType.MultipleChoice;
import static com.formlaez.infrastructure.enumeration.FormFieldType.StatusList;

@Component
@RequiredArgsConstructor
public class DataJsonConverter {

    private final ObjectMapper objectMapper;
    private final FormFieldValueConverter formFieldValueConverter;

    @Transactional(readOnly = true)
    public JsonNode convert(@Nullable JpaFormSubmission source) {
        return convert(source, new Options());
    }

    @Transactional(readOnly = true)
    public JsonNode convert(@Nullable JpaFormSubmission source, Options options) {
        if (Objects.isNull(source) || Objects.isNull(source.getData())) {
            return objectMapper.createObjectNode();
        }
        return process(source.getForm(), source.getData(), options);
    }

    private ObjectNode process(JpaForm form, JsonNode formData, Options options) {
        if (ObjectUtils.isEmpty(form.getPages())) {
            return objectMapper.createObjectNode();
        }
        ObjectNode root = objectMapper.createObjectNode();
        for (var page : form.getPages()) {
            var child = process(page, formData, options);
            root.setAll(child);
        }
        return root;
    }

    private ObjectNode process(JpaFormPage formPage, JsonNode formData, Options options) {
        if (ObjectUtils.isEmpty(formPage.getSections())) {
            return objectMapper.createObjectNode();
        }
        ObjectNode pageObjectNode = objectMapper.createObjectNode();
        for (var section : formPage.getSections()) {
            var child = process(section, formData, options);
            pageObjectNode.setAll(child);
        }
        return pageObjectNode;
    }

    private ObjectNode process(JpaFormSection section, JsonNode formData, Options options) {
        if (ObjectUtils.isEmpty(section.getFields())) {
            return objectMapper.createObjectNode();
        }

        if (section.getType() == FormSectionType.Single) {
            var field = section.getFields().get(0);
            if (!field.getType().isFormControl()) {
                return objectMapper.createObjectNode();
            }

            String key;
            if (options.isUseCodeAsKey()) {
                key = field.getCode();
            } else {
                key = field.getVariableName();
            }

            var fieldValue = process(field, formData, options);
            var fieldObject = objectMapper.createObjectNode();
            fieldObject.set(key, fieldValue);
            return fieldObject;
        }

        var sectionData = formData.get(section.getCode());
        if (!(sectionData instanceof ArrayNode sectionArrayData)) {
            return objectMapper.createObjectNode();
        }

        var fieldsObject = objectMapper.createObjectNode();
        var fieldsArrayNode = objectMapper.createArrayNode();

        for (var i = 0; i < sectionArrayData.size(); i++) {
            var itemData = sectionArrayData.get(i);
            var fieldObject = objectMapper.createObjectNode();
            for (var field : section.getFields()) {
                if (!field.getType().isFormControl()) {
                    continue;
                }

                var key = field.getCode();
                var fieldValue = process(field, itemData, options);
                fieldObject.set(key, fieldValue);
            }
            fieldsArrayNode.add(fieldObject);
        }
        fieldsObject.set(section.getVariableName(), fieldsArrayNode);
        return fieldsObject;
    }

    private JsonNode process(JpaFormField field, JsonNode fieldData, Options options) {
        if (!options.isKeepSelectionData() || !List.of(MultipleChoice, StatusList).contains(field.getType())) {
            String value = formFieldValueConverter.asTextValue(field, fieldData);
            return TextNode.valueOf(value);
        }

        return getMultiChoiceValue(field, fieldData);
    }

    private JsonNode getMultiChoiceValue(JpaFormField field, JsonNode rawValue) {
        var options = field.getOptions();
        if (ObjectUtils.isEmpty(options)) {
            return null;
        }
        var fieldValues = rawValue.get(field.getCode());
        if (!(fieldValues instanceof ArrayNode arrayValues)) {
            return null;
        }

        List<String> selectedValues = new ArrayList<>();
        for (var valueItem : arrayValues) {
            selectedValues.add(valueItem.asText());
        }
        var results = objectMapper.createArrayNode();
        for (var option : options) {
            if (selectedValues.contains(option.getCode())) {
                var selectedObject = objectMapper.createObjectNode();
                selectedObject.set("bgColor", TextNode.valueOf(option.getBgColor()));
                selectedObject.set("code", TextNode.valueOf(option.getCode()));
                selectedObject.set("label", TextNode.valueOf(option.getLabel()));
                results.add(selectedObject);
            }
        }
        return results;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Options {
        private boolean useCodeAsKey;
        private boolean keepSelectionData;
    }
}
