package com.formlaez.infrastructure.docengine.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ExpressionType {
    TABLE('#'),
    SIMPLE('$'),
    LOOP('@');

    private final Character selector;
}
