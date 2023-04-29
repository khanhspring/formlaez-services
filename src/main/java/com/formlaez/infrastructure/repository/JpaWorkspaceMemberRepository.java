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

public interface JpaWorkspaceMemberRepository extends JpaRepository<JpaWorkspaceMember, Long> {
    Page<JpaWorkspaceMember> findByWorkspaceId(Long workspaceId, Pageable pageable);
    Optional<JpaWorkspaceMember> findByUserIdAndWorkspaceId(String userId, Long workspaceId);
    boolean existsByUserIdAndWorkspaceId(String userId, Long workspaceId);
    List<JpaWorkspaceMember> findAllByUserId(String userId, Sort sort);

    boolean existsByRoleAndWorkspaceIdAndUserIdNot(WorkspaceMemberRole role, Long workspaceId, String userId);

    @Query("select o from JpaWorkspaceMember o" +
            " where" +
            " o.user.id = :userId" +
            " and o.workspace.createdBy = :userId")
    List<JpaWorkspaceMember> findAllByCreatedUserId(String userId);

    @Query("select o from JpaWorkspaceMember o" +
            " where 1=1" +
            " and o.workspace.id = :#{#request.workspaceId}" +
            " and (:#{#request.keyword == null} = true or o.user.firstName like %:#{#request.keyword}% or o.user.lastName like %:#{#request.keyword}% or o.user.email like %:#{#request.keyword}%)")
    Page<JpaWorkspaceMember> search(SearchWorkspaceMemberRequest request, Pageable pageable);
}