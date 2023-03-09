package com.formlaez.service.submission;

import com.formlaez.application.model.request.AdvanceSearchFormSubmissionRequest;
import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.SearchFormSubmissionRequest;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormSubmissionService {
    String create(CreateFormSubmissionRequest request);
    Page<FormSubmissionResponse> search(SearchFormSubmissionRequest request, Pageable pageable);
    Page<FormSubmissionResponse> searchAdvance(AdvanceSearchFormSubmissionRequest request, Pageable pageable);
}
