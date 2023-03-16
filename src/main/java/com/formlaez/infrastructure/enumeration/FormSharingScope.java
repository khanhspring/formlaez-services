package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormSharingScope {
    Private(0),
    Authenticated(1),
    Public(2);

    private final int level;
}
