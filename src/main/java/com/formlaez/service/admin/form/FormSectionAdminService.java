package com.formlaez.service.admin.form;

import com.formlaez.application.model.request.CreateFormSectionRequest;
import com.formlaez.application.model.request.MoveFormSectionRequest;
import com.formlaez.application.model.request.UpdateFormSectionRequest;

public interface FormSectionAdminService {
    Long create(CreateFormSectionRequest request);
    void update(UpdateFormSectionRequest request);
    void move(MoveFormSectionRequest request);
    void remove(String code);
}
