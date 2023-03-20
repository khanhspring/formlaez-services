package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaddleAlertName {
    subscription_created,
    subscription_cancelled,
    other;

    public static PaddleAlertName of(String name) {
        for (var alertName : values()) {
            if (alertName.name().equals(name)) {
                return alertName;
            }
        }
        return other;
    }
}
