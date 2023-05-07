package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PublishedFormAccessHelper {

    public void checkAccess(JpaForm form) {
        if (form.getStatus() != FormStatus.Published) {
            throw new ForbiddenException();
        }
        if (!form.isAcceptResponses()) {
            throw new ForbiddenException();
        }
        checkAuthenticatedAccess(form);
        checkUnauthenticatedAccess(form);
    }

    private boolean isFormOwner(JpaForm form) {
        var currentUserId = AuthUtils.currentUserId();
        return currentUserId.map(id -> id.equals(form.getCreatedBy()))
                .orElse(false);
    }

    private boolean isMemberOfTheTeamThatTheFormBelongTo(JpaForm form) {
        if (Objects.isNull(form.getTeam())) {
            return false;
        }
        var currentUserId = AuthUtils.currentUserId();
        if (currentUserId.isEmpty()) {
            return false;
        }
        return form.getTeam()
                .getMembers()
                .stream()
                .map(m -> m.getUser().getId())
                .anyMatch(uid -> uid.equals(currentUserId.get()));
    }

    private void checkAuthenticatedAccess(JpaForm form) {
        if (!AuthUtils.isAuthenticated()) {
            // return to continue next steps
            return;
        }
        if (isFormOwner(form)) {
            // the form owner can access their form in any sharing scope
            return;
        }
        if (isMemberOfTheTeamThatTheFormBelongTo(form)) {
            // the team members can access the form in any sharing scope
            return;
        }
        if (form.getSharingScope().getLevel() < FormSharingScope.Authenticated.getLevel()) {
            throw new ForbiddenException();
        }
    }

    private void checkUnauthenticatedAccess(JpaForm form) {
        if (AuthUtils.isAuthenticated()) {
            return;
        }
        if (form.getSharingScope() != FormSharingScope.Public) {
            throw new UnauthorizedException();
        }
    }
}
