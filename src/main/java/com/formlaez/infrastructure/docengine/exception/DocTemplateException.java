package com.formlaez.infrastructure.docengine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocTemplateException extends RuntimeException {
    private String message;
}
