package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PropertyPathChain extends PathChain {
    @Getter
    private final String content;

    @Override
    public Variable evaluate(Variable var) {
        if (var == null) {
            return null;
        }
        return var.getPropertyValue(content);
    }
}
