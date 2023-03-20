package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.SubscriptionStatus;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private WorkspaceType workspaceType;

    private Instant validFrom;
    private Instant validTill;

    private String cancelUrl;
    private SubscriptionStatus status;
}
