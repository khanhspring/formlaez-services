package com.formlaez.service.form;

import com.formlaez.application.model.response.FormDocumentTemplateResponse;

import java.util.List;

public interface FormDocumentTemplateService {

    List<FormDocumentTemplateResponse> getByFormId(Long formId);

}
