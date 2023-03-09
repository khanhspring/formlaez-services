package com.formlaez.service.form;

import com.formlaez.application.model.request.CreateFormSectionRequest;
import com.formlaez.application.model.request.MoveFormSectionRequest;

public interface FormSectionService {
    Long create(CreateFormSectionRequest request);
    void move(MoveFormSectionRequest request);
    void remove(String code);
}
