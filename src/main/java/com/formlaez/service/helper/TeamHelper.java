package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.repository.JpaTeamMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.formlaez.infrastructure.enumeration.TeamMemberRole.Owner;

@Component
@RequiredArgsConstructor
public class TeamHelper {

    private final JpaTeamMemberRepository jpaTeamMemberRepository;

    public void currentUserMustBeOwner(Long teamId) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var currentUserMember = jpaTeamMemberRepository.findByUserIdAndTeamId(currentUserId, teamId)
                .orElseThrow(ForbiddenException::new);

        List<TeamMemberRole> hasPermissionRoles = List.of(Owner);
        if (!hasPermissionRoles.contains(currentUserMember.getRole())) {
            throw new ForbiddenException();
        }
    }
}
