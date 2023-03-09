package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkspaceMemberRepository extends JpaRepository<JpaWorkspaceMember, Long> {
    Page<JpaWorkspaceMember> findByWorkspaceId(Long workspaceId, Pageable pageable);
    List<JpaWorkspaceMember> findAllByUserId(UUID userId);
    Optional<JpaWorkspaceMember> findByUserIdAndWorkspaceId(UUID userId, Long workspaceId);
    List<JpaWorkspaceMember> findAllByUserId(UUID userId, Sort sort);

    boolean existsByUserIdAndRole(UUID userId, WorkspaceMemberRole role);
}