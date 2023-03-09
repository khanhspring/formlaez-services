package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkspaceType {
    Free(1),
    Plus(2),
    Business(3),
    Enterprise(4);

    private final int level;
}
