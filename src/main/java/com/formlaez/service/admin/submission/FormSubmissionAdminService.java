package com.formlaez.service.admin.submission;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;

public interface FormSubmissionAdminService {
    Page<FormSubmissionResponse> search(SearchFormSubmissionRequest request, Pageable pageable);
    Page<FormSubmissionResponse> searchAdvance(AdvanceSearchFormSubmissionRequest request, Pageable pageable);
    void update(UpdateFormSubmissionRequest request);
    void archive(String submissionCode);
    byte[] mergeDocument(MergeDocumentFormSubmissionRequest request);
    void export(ExportFormSubmissionRequest request, PrintWriter writer);
}
