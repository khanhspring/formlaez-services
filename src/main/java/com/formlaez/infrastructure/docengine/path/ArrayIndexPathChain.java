package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ArrayIndexPathChain extends PathChain {
    @Getter
    private final Integer content;

    @Override
    public Variable evaluate(Variable var) {
        if (var == null) {
            return null;
        }
        return var.getPropertyValue(content - 1);
    }
}
