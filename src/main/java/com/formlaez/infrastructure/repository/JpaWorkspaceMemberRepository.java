package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchWorkspaceMemberRequest;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkspaceMemberRepository extends JpaRepository<JpaWorkspaceMember, Long> {
    Page<JpaWorkspaceMember> findByWorkspaceId(Long workspaceId, Pageable pageable);
    Optional<JpaWorkspaceMember> findByUserIdAndWorkspaceId(UUID userId, Long workspaceId);
    boolean existsByUserIdAndWorkspaceId(UUID userId, Long workspaceId);
    List<JpaWorkspaceMember> findAllByUserId(UUID userId, Sort sort);

    boolean existsByRoleAndWorkspaceIdAndUserIdNot(WorkspaceMemberRole role, Long workspaceId, UUID userId);

    @Query("select o from JpaWorkspaceMember o" +
            " where" +
            " o.user.id = :userId" +
            " and o.workspace.createdBy = :userId")
    List<JpaWorkspaceMember> findAllByCreatedUserId(UUID userId);

    @Query("select o from JpaWorkspaceMember o" +
            " where 1=1" +
            " and o.workspace.id = :#{#request.workspaceId}" +
            " and (:#{#request.keyword == null} = true or o.user.firstName like %:#{#request.keyword}% or o.user.lastName like %:#{#request.keyword}% or o.user.email like %:#{#request.keyword}%)")
    Page<JpaWorkspaceMember> search(SearchWorkspaceMemberRequest request, Pageable pageable);
}