package com.formlaez.infrastructure.model.entity;

import com.formlaez.infrastructure.enumeration.SubscriptionStatus;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class JpaSubscription extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private JpaWorkspace workspace;

    @Enumerated(EnumType.STRING)
    private WorkspaceType workspaceType;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private Instant validFrom;
    private Instant validTill;
    private Instant lastRenewedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribed_by")
    private JpaUser subscribedBy;

    private String cancelUrl;
    private String externalId;
}
