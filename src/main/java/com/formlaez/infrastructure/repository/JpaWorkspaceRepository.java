package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkspaceRepository extends JpaRepository<JpaWorkspace, Long> {

    Optional<JpaWorkspace> findByCode(String code);

    @Query("select o from JpaWorkspace o" +
            " where o.type != com.formlaez.infrastructure.enumeration.WorkspaceType.Free" +
            " and not exists (" +
            "   select 1 from JpaSubscription s" +
            "       where s.workspace.id = o.id" +
            "       and s.validFrom <= :currentDate" +
            "       and (s.validTill is null or s.validTill >= :currentDate)" +
            ")")
    Page<JpaWorkspace> getWorkspaceSubscriptionExpired(Instant currentDate, Pageable pageable);
}