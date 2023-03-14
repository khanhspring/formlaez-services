package com.formlaez.service.submission;

import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.MergeDocumentFormSubmissionRequest;

public interface FormSubmissionService {
    String create(CreateFormSubmissionRequest request);
    byte[] mergeDocument(MergeDocumentFormSubmissionRequest request);
}
