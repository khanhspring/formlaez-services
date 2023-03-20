package com.formlaez.service.user.impl;

import com.formlaez.application.model.response.MemberWorkspaceResponse;
import com.formlaez.application.model.response.UserSessionResponse;
import com.formlaez.infrastructure.converter.WorkspaceResponseConverter;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Owner;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final WorkspaceResponseConverter workspaceResponseConverter;

    @Override
    public UserSessionResponse getCurrentUserSession() {
        UUID currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var jpaWorkspaces = jpaWorkspaceMemberRepository.findAllByUserId(currentUserId,  Sort.by("createdDate"));
        var joinedWorkspaces = jpaWorkspaces.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        // TODO: get from DB
        var userWorkspace = joinedWorkspaces.stream()
                .filter(w -> w.getRole() == Owner)
                .findFirst()
                .orElse(null);

        var createdWorkspaceMembers = jpaWorkspaceMemberRepository.findAllByCreatedUserId(currentUserId);

        MemberWorkspaceResponse createdWorkspace = null;
        if (!ObjectUtils.isEmpty(createdWorkspaceMembers)) {
            var createdWorkspaceMember = createdWorkspaceMembers.get(0);
            createdWorkspace = toResponse(createdWorkspaceMember);
        }

        return UserSessionResponse.builder()
                .joinedWorkspaces(joinedWorkspaces)
                .lastAccessedWorkspace(userWorkspace)
                .createdWorkspace(createdWorkspace)
                .onboarded(Objects.nonNull(createdWorkspace))
                .build();
    }

    private MemberWorkspaceResponse toResponse(JpaWorkspaceMember workspaceMember) {
        var workspace = workspaceResponseConverter.convert(workspaceMember.getWorkspace());
        return MemberWorkspaceResponse.builder()
                .workspace(workspace)
                .role(workspaceMember.getRole())
                .joinedDate(workspaceMember.getCreatedDate())
                .build();
    }
}
