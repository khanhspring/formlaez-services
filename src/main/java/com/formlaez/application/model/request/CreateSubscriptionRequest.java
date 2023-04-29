package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionRequest {
    private Long workspaceId;
    private WorkspaceType workspaceType;

    private Instant validFrom;
    private Instant validTill;

    private String subscribedUserId;

    private String cancelUrl;
    private String externalId;
}
