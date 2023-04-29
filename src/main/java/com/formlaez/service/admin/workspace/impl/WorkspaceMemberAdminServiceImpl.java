package com.formlaez.service.admin.workspace.impl;

import com.formlaez.application.model.request.AddWorkspaceMemberRequest;
import com.formlaez.application.model.request.RemoveWorkspaceMemberRequest;
import com.formlaez.application.model.request.SearchWorkspaceMemberRequest;
import com.formlaez.application.model.request.UpdateWorkspaceMemberRoleRequest;
import com.formlaez.application.model.response.WorkspaceMemberResponse;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.UserResponseConverter;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import com.formlaez.infrastructure.repository.JpaTeamMemberRepository;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.admin.workspace.WorkspaceMemberAdminService;
import com.formlaez.service.helper.WorkspaceHelper;
import com.formlaez.service.usage.WorkspaceUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Member;
import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Owner;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceMemberAdminServiceImpl implements WorkspaceMemberAdminService {

    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaTeamMemberRepository jpaTeamMemberRepository;
    private final UserResponseConverter userResponseConverter;
    private final WorkspaceHelper workspaceHelper;
    private final WorkspaceUsageService workspaceUsageService;

    @Override
    @Transactional
    public Long add(AddWorkspaceMemberRequest request) {
        workspaceUsageService.checkMemberLimitAndIncreaseOrElseThrow(request.getWorkspaceId());
        workspaceHelper.currentUserMustBeOwner(request.getWorkspaceId());

        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(InvalidParamsException::new);
        var user = jpaUserRepository.findById(request.getUserId())
                .orElseThrow(InvalidParamsException::new);

        var member = JpaWorkspaceMember.builder()
                .user(user)
                .workspace(workspace)
                .role(request.getRole())
                .build();
        return jpaWorkspaceMemberRepository.save(member)
                .getId();
    }

    @Override
    @Transactional
    public void remove(RemoveWorkspaceMemberRequest request) {
        workspaceHelper.currentUserMustBeOwner(request.getWorkspaceId());

        var existing = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(request.getUserId(), request.getWorkspaceId());
        if (existing.isEmpty()) {
            return;
        }
        var member = existing.get();

        if (member.getRole() == Owner) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            var hasOtherAdmin = jpaWorkspaceMemberRepository.existsByRoleAndWorkspaceIdAndUserIdNot(Owner, request.getWorkspaceId(), currentUserId);
            if (!hasOtherAdmin) {
                log.error("Can not remove last admin of the workflow id [{}]", request.getWorkspaceId());
                throw new InvalidParamsException();
            }
        }
        workspaceUsageService.decreaseMember(request.getWorkspaceId());
        jpaTeamMemberRepository.deleteAllByUserIdAndTeamWorkspaceId(request.getUserId(), request.getWorkspaceId());
        jpaWorkspaceMemberRepository.delete(member);
    }

    @Override
    @Transactional
    public void updateRole(UpdateWorkspaceMemberRoleRequest request) {
        workspaceHelper.currentUserMustBeOwner(request.getWorkspaceId());

        var existing = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(request.getUserId(), request.getWorkspaceId())
                .orElseThrow(ResourceNotFoundException::new);

        if (existing.getRole() == Owner && request.getRole() == Member) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            var hasOtherAdmin = jpaWorkspaceMemberRepository.existsByRoleAndWorkspaceIdAndUserIdNot(Owner, request.getWorkspaceId(), currentUserId);
            if (!hasOtherAdmin) {
                log.error("Can not update last admin to role of member of the workflow id [{}]", request.getWorkspaceId());
                throw new InvalidParamsException();
            }
        }

        existing.setRole(request.getRole());
        jpaWorkspaceMemberRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkspaceMemberResponse> search(SearchWorkspaceMemberRequest request, Pageable pageable) {
        var memberPage = jpaWorkspaceMemberRepository.search(request, pageable);
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
