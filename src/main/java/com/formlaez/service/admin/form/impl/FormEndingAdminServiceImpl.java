package com.formlaez.service.admin.form.impl;

import com.formlaez.application.model.request.UpdateFormEndingRequest;
import com.formlaez.application.model.response.form.FormEndingResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.FormEndingResponseConverter;
import com.formlaez.infrastructure.model.entity.form.JpaFormEnding;
import com.formlaez.infrastructure.repository.JpaFormEndingRepository;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.service.admin.form.FormEndingAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class FormEndingAdminServiceImpl implements FormEndingAdminService {

    private final JpaFormEndingRepository jpaFormEndingRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormEndingResponseConverter formEndingResponseConverter;

    @Override
    @Transactional
    public void upsert(UpdateFormEndingRequest request) {
        var form = jpaFormRepository.findById(request.getFormId())
                .orElseThrow(ResourceNotFoundException::new);

        var formEnding = jpaFormEndingRepository.findByFormId(form.getId())
                .orElse(JpaFormEnding.builder()
                        .form(form)
                        .build());

        formEnding.setContent(request.getContent());
        formEnding.setHideButton(request.isHideButton());
        jpaFormEndingRepository.save(formEnding);
    }

    @Override
    @GetMapping
    public FormEndingResponse getByFormId(Long formId) {
        var formEnding = jpaFormEndingRepository.findByFormId(formId)
                .orElse(JpaFormEnding.builder().build());

        return formEndingResponseConverter.convert(formEnding);
    }
}
