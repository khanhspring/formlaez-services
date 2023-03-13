package com.formlaez.infrastructure.docengine.expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionExtractor {
    private final List<IExpressionExtractor> extractors;

    {
        extractors = new ArrayList<>();
        extractors.add(new SimpleExpressionExtractor());
        extractors.add(new LoopExpressionExtractor());
        extractors.add(new TableExpressionExtractor());
    }

    public Expression extractFromText(String text) {
        for (IExpressionExtractor extractor : extractors) {
            Expression expression = extractor.extractFromText(text);
            if (expression != null) {
                return expression;
            }
        }
        return null;
    }
}
