package com.formlaez.infrastructure.docengine.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidVariableException extends DocTemplateException {
    public InvalidVariableException(String message) {
        super(message);
    }
}
