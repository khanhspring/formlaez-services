package com.formlaez.infrastructure.docengine.expression.attribute;

import com.formlaez.infrastructure.docengine.exception.InvalidVariableException;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ExpressionAttribute {
    private String fullContent;
    private List<AttributeProperty> properties = new ArrayList<>();
    private Map<String, AttributeProperty> propertiesMap = new HashMap<>();

    public ExpressionAttribute(String fullContent) {
        this.fullContent = fullContent;
    }

    public void addAttr(String key, String val) {
        AttributeProperty attr = AttributeProperty.builder()
                .key(key)
                .value(val)
                .build();
        properties.add(attr);
        propertiesMap.put(key, attr);
    }

    public Integer getAttrAsInteger(String key) {
        String value = getAttrAsString(key);
        if (value == null) {
            return null;
        }
        if (!value.matches("[0-9]+")) {
            throw new InvalidVariableException("Expression property is not a number!");
        }
        return Integer.valueOf(value);
    }

    public String getAttrAsString(String key) {
        AttributeProperty property = propertiesMap.get(key);
        if (property == null) {
            return null;
        }
        return property.getValue();
    }
}
