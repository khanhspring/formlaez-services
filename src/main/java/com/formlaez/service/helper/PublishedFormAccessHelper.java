package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
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
        if (!isFormOwner(form) && form.getSharingScope() != FormSharingScope.Public) {
            throw new ForbiddenException();
        }
    }

    private boolean isFormOwner(JpaForm form) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        return currentUserId.equals(form.getCreatedBy());
    }
}
