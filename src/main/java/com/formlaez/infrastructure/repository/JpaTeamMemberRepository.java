package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTeamMemberRepository extends JpaRepository<JpaTeamMember, Long> {

    Page<JpaTeamMember> findByTeamId(Long teamId, Pageable pageable);
    Optional<JpaTeamMember> findByUserIdAndTeamId(UUID userId, Long teamId);
    boolean existsByUserIdAndTeamId(UUID userId, Long teamId);
}