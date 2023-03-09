package com.formlaez.infrastructure.model.projection;

import com.formlaez.infrastructure.model.entity.JpaUser;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;

public interface JpaFormSubmissionProjection {
    JpaFormSubmission getFormSubmission();
    JpaUser getCreatedBy();
    JpaUser getLastModifiedBy();
}
