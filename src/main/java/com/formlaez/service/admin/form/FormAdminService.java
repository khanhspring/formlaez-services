package com.formlaez.service.admin.form;

import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.request.UpdateFormSettingsRequest;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormAdminService {
    Page<BasicFormResponse> search(SearchFormRequest request, Pageable pageable);
    BasicFormResponse findByCode(String code);
    FormResponse getDetail(String code);
    String create(CreateFormRequest request);
    void update(UpdateFormRequest request);
    void updateSettings(UpdateFormSettingsRequest request);
    void publish(Long id);
    void archive(Long id);
    void delete(Long id);
}
