package com.formlaez.service.admin.form;

import com.formlaez.application.model.request.CreateFormFieldRequest;
import com.formlaez.application.model.request.MoveFormFieldRequest;
import com.formlaez.application.model.request.UpdateFormFieldRequest;

public interface FormFieldAdminService {
    Long create(CreateFormFieldRequest request);

    void update(UpdateFormFieldRequest request);

    void move(MoveFormFieldRequest request);

    void remove(String code);
}
