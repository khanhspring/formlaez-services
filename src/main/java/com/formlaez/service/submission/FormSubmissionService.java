package com.formlaez.service.submission;

import com.formlaez.application.model.request.CreateFormSubmissionRequest;

public interface FormSubmissionService {
    String create(CreateFormSubmissionRequest request);
}
