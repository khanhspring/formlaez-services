package com.formlaez.service.admin.workspace.impl;

import com.formlaez.application.model.request.CreateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceTypeRequest;
import com.formlaez.application.model.response.WorkspaceResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.DuplicatedException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.admin.workspace.WorkspaceAdminService;
import com.formlaez.service.helper.WorkspaceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceAdminServiceImpl implements WorkspaceAdminService {
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final JpaUserRepository jpaUserRepository;
    private final WorkspaceHelper workspaceHelper;

    @Override
    @Transactional
    public Long create(CreateWorkspaceRequest request) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var existing = jpaWorkspaceMemberRepository.existsByUserIdAndRole(currentUserId, WorkspaceMemberRole.Owner);
        if (existing) {
            throw new DuplicatedException();
        }

        var entity = JpaWorkspace.builder()
                .code(RandomUtils.randomNanoId(7))
                .description(request.getDescription())
                .name(request.getName())
                .type(WorkspaceType.Free)
                .build();
        var workspace = jpaWorkspaceRepository.save(entity);

        var currentUser = jpaUserRepository.findById(currentUserId)
                .orElseThrow(UnauthorizedException::new);

        var owner = JpaWorkspaceMember.builder()
                .user(currentUser)
                .workspace(workspace)
                .role(WorkspaceMemberRole.Owner)
                .build();

        jpaWorkspaceMemberRepository.save(owner);
        return workspace.getId();
    }

    @Override
    public void update(UpdateWorkspaceRequest request) {
        var existing = jpaWorkspaceRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        workspaceHelper.currentUserMustBeOwnerOrAdmin(request.getId());

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        jpaWorkspaceRepository.save(existing);
    }

    @Override
    public void upgrade(UpdateWorkspaceTypeRequest request) {
        var existing = jpaWorkspaceRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        if (request.getType().getLevel() <= existing.getType().getLevel()) {
            throw new ApplicationException("Workspace type to upgrade is invalid");
        }

        existing.setType(request.getType());
        jpaWorkspaceRepository.save(existing);
    }

    @Override
    public void downgrade(UpdateWorkspaceTypeRequest request) {
        var existing = jpaWorkspaceRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        workspaceHelper.currentUserMustBeOwnerOrAdmin(request.getId());

        if (request.getType().getLevel() >= existing.getType().getLevel()) {
            throw new ApplicationException("Workspace type to downgrade is invalid");
        }

        existing.setType(request.getType());
        jpaWorkspaceRepository.save(existing);
    }

    @Override
    public WorkspaceResponse getById(Long id) {
        var existing = jpaWorkspaceRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: check access
        return WorkspaceResponse.builder()
                .id(existing.getId())
                .code(existing.getCode())
                .description(existing.getDescription())
                .name(existing.getName())
                .type(existing.getType())
                .createdDate(existing.getCreatedDate())
                .lastModifiedDate(existing.getLastModifiedDate())
                .build();
    }

    @Override
    public WorkspaceResponse getByCode(String code) {
        var existing = jpaWorkspaceRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: check access
        return WorkspaceResponse.builder()
                .id(existing.getId())
                .code(existing.getCode())
                .description(existing.getDescription())
                .name(existing.getName())
                .type(existing.getType())
                .createdDate(existing.getCreatedDate())
                .lastModifiedDate(existing.getLastModifiedDate())
                .build();
    }
}
