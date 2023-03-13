package com.formlaez.infrastructure.docengine.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoopExpressionExtractor implements IExpressionExtractor {

    private static final Pattern PATTERN = Pattern.compile("^.*(\\@\\{(?!\\/)(.+?)\\}).*$");
    private static final String END_SIGNAL = "@{/}";

    @Override
    public ExpressionType getType() {
        return ExpressionType.LOOP;
    }

    @Override
    public Expression extractFromText(String text) {
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            return null;
        }
        return Expression.builder()
                .type(ExpressionType.LOOP)
                .content(matcher.group(2))
                .fullExpression(matcher.group(1))
                .build();
    }

    public boolean isEnd(String text) {
        return text.trim().equals(END_SIGNAL);
    }
}
