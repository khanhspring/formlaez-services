package com.formlaez.infrastructure.docengine.expression;

public class ExpressionBuilder {
    public String build(ExpressionType type, String... items) {
        StringBuilder expression = new StringBuilder();

        for (String item : items) {
            if (expression.length() != 0) {
                expression.append(ExpressionConfig.DELIMITER.toString());
            }
            expression.append(item);
        }

        return type.getSelector().toString()
                + ExpressionConfig.OPEN.toString()
                + expression.toString()
                + ExpressionConfig.CLOSE.toString();
    }
}
