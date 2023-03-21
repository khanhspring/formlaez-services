package com.formlaez.service.admin.form;

import com.formlaez.application.model.request.CreateFormDocumentTemplateRequest;
import com.formlaez.application.model.request.SearchFormDocumentTemplateRequest;
import com.formlaez.application.model.request.UpdateFormDocumentTemplateRequest;
import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormDocumentTemplateAdminService {

    Page<FormDocumentTemplateResponse> search(SearchFormDocumentTemplateRequest request, Pageable pageable);

    Long create(CreateFormDocumentTemplateRequest request);

    void update(UpdateFormDocumentTemplateRequest request);
    void remove(Long id);
}
