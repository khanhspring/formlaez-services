package com.formlaez.infrastructure.docengine.util;

public class EscapeUtils {
    public static String escape(char c) {
        switch (c) {
            case '.': return "\\.";
            case '[': return "\\[";
            case ']': return "\\]";
            case '$': return "\\$";
            case '@': return "\\@";
            case '#': return "\\#";
            case '(': return "\\(";
            case ')': return "\\)";
            case '{': return "\\{";
            case '}': return "\\}";
        }
        return String.valueOf(c);
    }
}
