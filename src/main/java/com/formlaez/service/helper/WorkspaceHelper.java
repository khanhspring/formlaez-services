package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Admin;
import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Owner;

@Component
@RequiredArgsConstructor
public class WorkspaceHelper {

    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;

    public void currentUserMustBeOwnerOrAdmin(Long workspaceId) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var currentUserMember = jpaWorkspaceMemberRepository.findByUserIdAndWorkspaceId(currentUserId, workspaceId)
                .orElseThrow(ForbiddenException::new);

        List<WorkspaceMemberRole> hasPermissionRoles = List.of(Owner, Admin);
        if (!hasPermissionRoles.contains(currentUserMember.getRole())) {
            throw new ForbiddenException();
        }
    }
}
