package com.formlaez.infrastructure.docengine.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CloneRowException extends DocTemplateException {
    public CloneRowException(String message) {
        super(message);
    }
}
