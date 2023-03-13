package com.formlaez.infrastructure.docengine.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPathException extends DocTemplateException {
    public InvalidPathException (String message) {
        super(message);
    }
}
