package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface JpaTeamMemberRepository extends JpaRepository<JpaTeamMember, Long> {

    Page<JpaTeamMember> findByTeamId(Long teamId, Pageable pageable);

    Optional<JpaTeamMember> findByUserIdAndTeamId(String userId, Long teamId);

    boolean existsByUserIdAndTeamId(String userId, Long teamId);

    boolean existsByRoleAndTeamIdAndUserIdNot(TeamMemberRole role, Long teamId, String userId);

    @Modifying
    void deleteAllByUserIdAndTeamWorkspaceId(String userId, Long workspaceId);
}