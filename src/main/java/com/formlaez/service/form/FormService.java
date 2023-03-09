package com.formlaez.service.form;

import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormService {
    Page<BasicFormResponse> search(SearchFormRequest request, Pageable pageable);
    BasicFormResponse findByCode(String code);
    FormResponse getDetail(String code);
    Long create(CreateFormRequest request);
    void update(UpdateFormRequest request);
    void archive(Long id);
}
