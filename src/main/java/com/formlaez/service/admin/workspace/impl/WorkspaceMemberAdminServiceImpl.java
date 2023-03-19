package com.formlaez.service.admin.workspace.impl;

import com.formlaez.application.model.request.AddWorkspaceMemberRequest;
import com.formlaez.application.model.request.RemoveWorkspaceMemberRequest;
import com.formlaez.application.model.request.SearchWorkspaceMemberRequest;
import com.formlaez.application.model.request.UpdateWorkspaceMemberRoleRequest;
import com.formlaez.application.model.response.WorkspaceMemberResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.UserResponseConverter;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.service.helper.WorkspaceHelper;
import com.formlaez.service.admin.workspace.WorkspaceMemberAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Owner;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberAdminServiceImpl implements WorkspaceMemberAdminService {

    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserResponseConverter userResponseConverter;
    private final WorkspaceHelper workspaceHelper;

    @Override
    public Long add(AddWorkspaceMemberRequest request) {
        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(InvalidParamsException::new);
        var user = jpaUserRepository.findById(request.getUserId())
                .orElseThrow(InvalidParamsException::new);

        Assert.isTrue(request.getRole() != Owner, "Can not add a member as workspace Owner");
        var member = JpaWorkspaceMember.builder()
                .user(user)
                .workspace(workspace)
                .role(request.getRole())
                .build();
        return jpaWorkspaceMemberRepository.save(member)
                .getId();
    }

    @Override
    public void remove(RemoveWorkspaceMemberRequest request) {
        var existing = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(request.getUserId(), request.getWorkspaceId());
        if (existing.isEmpty()) {
            return;
        }
        var member = existing.get();
        if (member.getRole() == Owner) {
            throw new ApplicationException("Can not remove the owner from their workspace");
        }
        workspaceHelper.currentUserMustBeOwnerOrAdmin(request.getWorkspaceId());

        jpaWorkspaceMemberRepository.delete(member);
    }

    @Override
    public void updateRole(UpdateWorkspaceMemberRoleRequest request) {
        var existing = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(request.getUserId(), request.getWorkspaceId())
                .orElseThrow(ResourceNotFoundException::new);

        workspaceHelper.currentUserMustBeOwnerOrAdmin(request.getWorkspaceId());

        existing.setRole(request.getRole());
        jpaWorkspaceMemberRepository.delete(existing);
    }

    @Override
    public Page<WorkspaceMemberResponse> search(SearchWorkspaceMemberRequest request, Pageable pageable) {
        var memberPage = jpaWorkspaceMemberRepository.findByWorkspaceId(request.getWorkspaceId(), pageable);
        return memberPage.map(this::toResponse);
    }

    private WorkspaceMemberResponse toResponse(JpaWorkspaceMember member) {
        return WorkspaceMemberResponse.builder()
                .user(userResponseConverter.convert(member.getUser()))
                .role(member.getRole())
                .joinedDate(member.getCreatedDate())
                .build();
    }
}
