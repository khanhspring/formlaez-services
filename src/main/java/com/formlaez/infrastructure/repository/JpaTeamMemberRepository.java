package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTeamMemberRepository extends JpaRepository<JpaTeamMember, Long> {

    Page<JpaTeamMember> findByTeamId(Long teamId, Pageable pageable);

    Optional<JpaTeamMember> findByUserIdAndTeamId(UUID userId, Long teamId);

    boolean existsByUserIdAndTeamId(UUID userId, Long teamId);

    boolean existsByRoleAndTeamIdAndUserIdNot(TeamMemberRole role, Long teamId, UUID userId);

    @Modifying
    void deleteAllByUserIdAndTeamWorkspaceId(UUID userId, Long workspaceId);
}