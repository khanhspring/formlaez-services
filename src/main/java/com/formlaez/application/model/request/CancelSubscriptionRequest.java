package com.formlaez.application.model.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelSubscriptionRequest {
    private String externalId;
    private Instant cancellationEffectiveDate;
}
