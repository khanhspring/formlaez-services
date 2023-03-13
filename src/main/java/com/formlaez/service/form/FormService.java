package com.formlaez.service.form;

import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;

public interface FormService {
    BasicFormResponse findByCode(String code);
    FormResponse getDetail(String code);
}
