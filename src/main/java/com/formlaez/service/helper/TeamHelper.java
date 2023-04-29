package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.repository.JpaTeamMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.formlaez.infrastructure.enumeration.TeamMemberRole.Owner;

@Component
@RequiredArgsConstructor
public class TeamHelper {

    private final JpaTeamMemberRepository jpaTeamMemberRepository;

    public void currentUserMustBeOwner(Long teamId) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var currentUserMember = jpaTeamMemberRepository.findByUserIdAndTeamId(currentUserId, teamId)
                .orElseThrow(ForbiddenException::new);

        if (currentUserMember.getRole() != Owner) {
            throw new ForbiddenException();
        }
    }
}
