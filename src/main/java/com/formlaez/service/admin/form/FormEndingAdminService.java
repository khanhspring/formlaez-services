package com.formlaez.service.admin.form;

import com.formlaez.application.model.request.UpdateFormEndingRequest;
import com.formlaez.application.model.response.form.FormEndingResponse;

public interface FormEndingAdminService {
    void upsert(UpdateFormEndingRequest request);
    FormEndingResponse getByFormId(Long formId);
}
