package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return currentUserId.map(uuid -> uuid.equals(form.getCreatedBy()))
                .orElse(false);
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
