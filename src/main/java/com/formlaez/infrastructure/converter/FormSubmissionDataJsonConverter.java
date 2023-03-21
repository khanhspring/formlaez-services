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

    private final ObjectMapper objectMapper;
    private final FormFieldValueConverter formFieldValueConverter;

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public JsonNode convert(@Nullable JpaFormSubmission source) {
        if (Objects.isNull(source) || Objects.isNull(source.getData())) {
            return objectMapper.createObjectNode();
        }
        return process(source.getForm(), source.getData());
    }

    private ObjectNode process(JpaForm form, JsonNode formData) {
        if (ObjectUtils.isEmpty(form.getPages())) {
            return objectMapper.createObjectNode();
        }
        ObjectNode root = objectMapper.createObjectNode();
        for (var page : form.getPages()) {
            var child = process(page, formData);
            root.setAll(child);
        }
        return root;
    }

    private ObjectNode process(JpaFormPage formPage, JsonNode formData) {
        if (ObjectUtils.isEmpty(formPage.getSections())) {
            return objectMapper.createObjectNode();
        }
        ObjectNode pageObjectNode = objectMapper.createObjectNode();
        for (var section : formPage.getSections()) {
            var child = process(section, formData);
            pageObjectNode.setAll(child);
        }
        return pageObjectNode;
    }

    private ObjectNode process(JpaFormSection section, JsonNode formData) {
        if (ObjectUtils.isEmpty(section.getFields())) {
            return objectMapper.createObjectNode();
        }

        if (section.getType() == FormSectionType.Single) {
            var field = section.getFields().get(0);
            if (!field.getType().isFormControl()) {
                return objectMapper.createObjectNode();
            }

            var key = field.getVariableName();
            var fieldValue = process(field, formData);
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

                var key = field.getVariableName();
                var fieldValue = process(field, itemData);
                fieldObject.set(key, fieldValue);
            }
            fieldsArrayNode.add(fieldObject);
        }
        fieldsObject.set(section.getVariableName(), fieldsArrayNode);
        return fieldsObject;
    }

    private JsonNode process(JpaFormField field, JsonNode fieldData) {
        String value = formFieldValueConverter.asTextValue(field, fieldData);
        return TextNode.valueOf(value);
    }
}
