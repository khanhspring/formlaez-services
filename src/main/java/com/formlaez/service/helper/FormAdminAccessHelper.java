package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.repository.JpaTeamMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FormAdminAccessHelper {

    private final JpaTeamMemberRepository jpaTeamMemberRepository;

    @Transactional(readOnly = true)
    public void checkAccess(JpaForm form) {
        if (form.getScope() == FormScope.Private) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            if (!currentUserId.equals(form.getCreatedBy())) {
                throw new ForbiddenException();
            }
        }

        if (form.getScope() == FormScope.Team) {
            var team = form.getTeam();
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            var isTeamMember = jpaTeamMemberRepository.existsByUserIdAndTeamId(currentUserId, team.getId());
            if (!isTeamMember) {
                throw new ForbiddenException();
            }
        }
    }
}
