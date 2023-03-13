package com.formlaez.infrastructure.docengine.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleExpressionExtractor implements IExpressionExtractor {

    private static final Pattern PATTERN = Pattern.compile("^.*(\\$\\{(?!\\/)(.+?)\\}).*$");

    @Override
    public ExpressionType getType() {
        return ExpressionType.SIMPLE;
    }

    @Override
    public Expression extractFromText(String text) {
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            return null;
        }
        return Expression.builder()
                .type(ExpressionType.SIMPLE)
                .content(matcher.group(2))
                .fullExpression(matcher.group(1))
                .build();
    }
}
