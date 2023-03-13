package com.formlaez.infrastructure.docengine.common;

import lombok.Data;

@Data
public class SequenceIterator {
    private CharSequence content;
    private int position = -1;

    public SequenceIterator(CharSequence content) {
        this.content = content;
    }

    public char charAt(int pos) {
        return content.charAt(pos);
    }

    public char currentChar() {
        return content.charAt(position);
    }
    public char nextChar() {
        return content.charAt(position + 1);
    }

    public boolean inBounds(int idx) {
        return (idx >= 0) && (idx < content.length());
    }

    public boolean currentCharIs(char c) {
        char current = currentChar();
        return c == current;
    }

    public boolean hasNext() {
        return inBounds(position + 1);
    }

    public String subSequence(int start, int end) {
        return content.subSequence(start, end).toString();
    }

    public void next(int step) {
        setPosition(position + step);
    }

    public void next() {
        setPosition(position + 1);
    }
    public void back() {
        setPosition(position - 1);
    }
}
