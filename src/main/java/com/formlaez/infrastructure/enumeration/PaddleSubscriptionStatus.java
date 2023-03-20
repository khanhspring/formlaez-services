package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaddleSubscriptionStatus {
    active,
    trialing,
    past_due,
    paused,
    deleted
}
