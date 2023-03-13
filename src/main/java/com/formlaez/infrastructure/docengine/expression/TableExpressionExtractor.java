package com.formlaez.infrastructure.docengine.expression;

import com.formlaez.infrastructure.docengine.expression.attribute.AttributeCompiler;
import com.formlaez.infrastructure.docengine.expression.attribute.ExpressionAttribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableExpressionExtractor implements IExpressionExtractor {

    private static final Pattern PATTERN = Pattern.compile("^.*(\\#\\{(?!\\/)(.+?)\\})(<(.*)>)?.*$");

    @Override
    public ExpressionType getType() {
        return ExpressionType.TABLE;
    }

    @Override
    public Expression extractFromText(String text) {
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            return null;
        }
        ExpressionAttribute attribute = null;
        if (matcher.group(4) != null) {
            AttributeCompiler attributeCompiler = new AttributeCompiler(matcher.group(4));
            attribute = attributeCompiler.compile();
        }
        return Expression.builder()
                .type(ExpressionType.TABLE)
                .content(matcher.group(2))
                .fullExpression(matcher.group(1))
                .attribute(attribute)
                .build();
    }
}
