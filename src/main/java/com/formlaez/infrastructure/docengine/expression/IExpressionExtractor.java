package com.formlaez.infrastructure.docengine.expression;

public interface IExpressionExtractor {
    ExpressionType getType();
    Expression extractFromText(String text);
}
