package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface JpaSubscriptionRepository extends JpaRepository<JpaSubscription, Long> {

    Optional<JpaSubscription> findByExternalId(String externalId);

    @Query("select o from JpaSubscription o" +
            " where o.workspace.id = :workspaceId" +
            " and o.validFrom <= :currentDate" +
            " and (o.validTill is null or o.validTill >= :currentDate)")
    Optional<JpaSubscription> getCurrentByWorkspaceId(Long workspaceId, Instant currentDate);
}