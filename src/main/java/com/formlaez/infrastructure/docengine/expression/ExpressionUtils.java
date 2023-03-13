package com.formlaez.infrastructure.docengine.expression;

public class ExpressionUtils {

    public static boolean isSelector(char c) {
        for (ExpressionType type : ExpressionType.values()) {
            if (type.getSelector() == c) {
                return true;
            }
        }
        return false;
    }

    public static int selectorIndex(String text) {
        if (text == null) {
            return -1;
        }
        for (ExpressionType type : ExpressionType.values()) {
            int index = text.indexOf(type.getSelector());
            if (index > -1) {
                return index;
            }
        }
        return -1;
    }

    public static int openIndex(String text) {
        if (text == null) {
            return -1;
        }
        return text.indexOf(ExpressionConfig.OPEN);
    }

    public static int closeIndex(String text) {
        if (text == null) {
            return -1;
        }
        return text.indexOf(ExpressionConfig.CLOSE);
    }

    public static boolean isOpen(char c) {
        return c == ExpressionConfig.OPEN;
    }

    public static boolean isClose(char c) {
        return c == ExpressionConfig.CLOSE;
    }
}
