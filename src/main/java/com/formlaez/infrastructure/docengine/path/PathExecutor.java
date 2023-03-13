package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PathExecutor {

    public String execute(String pathStr, Variable vars) {
        PathCompiler pathCompiler = new PathCompiler(pathStr);
        PathChain pathChain = pathCompiler.compile();
        PathEvaluator evaluator = new PathEvaluator(pathChain, vars);
        return evaluator.evaluate();
    }
}
