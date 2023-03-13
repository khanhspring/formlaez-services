package com.formlaez.infrastructure.docengine.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TableAttributeKeys {
    ROW("r"); // start from row

    private final String keyName;
}
