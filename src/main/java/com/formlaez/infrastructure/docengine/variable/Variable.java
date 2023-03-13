package com.formlaez.infrastructure.docengine.variable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Variable {
    @Getter
    private final JsonNode value;
    @Getter
    private Variable root;

    public Variable(JsonNode value) {
        this.value = value;
    }

    public static Variable of(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode value = objectMapper.readTree(json);
        return new Variable(value, new Variable(value));
    }

    public static Variable of(JsonNode value) {
        return new Variable(value, new Variable(value));
    }

    public Variable getPropertyValue(String property) {
        return new Variable(value.get(property), root);
    }

    public Variable getPropertyValue(int property) {
        return new Variable(value.get(property), root);
    }

    public boolean isPresent() {
        return value != null && !value.isNull();
    }

    public String asText() {
        if (isPresent()) {
            if (value.isValueNode()) {
                return value.asText();
            }

            if (value.isObject()) {
                return tryToConvertAsText(value);
            }

            if (isArray()) {
                StringBuilder result = new StringBuilder();
                var arr = (ArrayNode) value;
                for (var item : arr) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    result.append(tryToConvertAsText(item));
                }
                return result.toString();
            }
        }
        return null;
    }

    public String tryToConvertAsText(JsonNode val) {
        var text = val.asText();
        if (text != null && !text.isEmpty()) {
            return text;
        }
        // try to find label or value
        if (val.get("label") != null) {
            text = val.get("label").asText();
        }
        if (text != null && !text.isEmpty()) {
            return text;
        }

        if (val.get("value") != null) {
            text = val.get("value").asText();
        }

        return text;
    }

    public boolean isArray() {
        return value instanceof ArrayNode;
    }

    public int arrayLength() {
        if (!isArray()) {
            return 0;
        }
        return value.size();
    }

    public boolean isMap() {
        return value instanceof ObjectNode;
    }
}
