package com.formlaez.service.form;

import com.formlaez.application.model.request.CreateFormFieldRequest;
import com.formlaez.application.model.request.MoveFormFieldRequest;
import com.formlaez.application.model.request.UpdateFormFieldRequest;

public interface FormFieldService {
    Long create(CreateFormFieldRequest request);
    void update(UpdateFormFieldRequest request);
    void move(MoveFormFieldRequest request);
}
