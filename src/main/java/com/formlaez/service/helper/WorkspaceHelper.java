package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceHelper {

    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;

    public void currentUserMustBeOwner(Long workspaceId) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var currentUserMember = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(currentUserId, workspaceId)
                .orElseThrow(ForbiddenException::new);

        if (currentUserMember.getRole() != WorkspaceMemberRole.Owner) {
            throw new ForbiddenException();
        }
    }
}
