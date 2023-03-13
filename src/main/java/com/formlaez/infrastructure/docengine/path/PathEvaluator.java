package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PathEvaluator {
    private PathChain path;
    private Variable var;

    public String evaluate() {
        while(!path.isLeaf()) {
            if (!var.isPresent()) {
                return "";
            }
            var = path.evaluate(var);
            path = path.next();
        }
        if (!var.isPresent()) {
            return "";
        }
        var = path.evaluate(var);
        var val = var.asText();
        if (val != null) {
            return val;
        }
        return "";
    }

    public void reset() {
        path = path.getRoot();
        var = var.getRoot();
    }
}
