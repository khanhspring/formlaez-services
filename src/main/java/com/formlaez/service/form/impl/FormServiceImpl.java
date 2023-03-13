package com.formlaez.service.form.impl;

import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.BasicFormResponseConverter;
import com.formlaez.infrastructure.converter.FormResponseConverter;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.service.form.FormService;
import com.formlaez.service.helper.PublishedFormAccessHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final JpaFormRepository jpaFormRepository;
    private final FormResponseConverter formResponseConverter;
    private final BasicFormResponseConverter basicFormResponseConverter;
    private final PublishedFormAccessHelper publishedFormAccessHelper;

    @Override
    @Transactional(readOnly = true)
    public BasicFormResponse findByCode(String code) {
        var form = jpaFormRepository.findByCodeAndStatus(code, FormStatus.Published)
                .orElseThrow(ResourceNotFoundException::new);
        publishedFormAccessHelper.checkAccess(form);
        return basicFormResponseConverter.convert(form);
    }

    @Override
    @Transactional(readOnly = true)
    public FormResponse getDetail(String code) {
        var form = jpaFormRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);
        publishedFormAccessHelper.checkAccess(form);
        return formResponseConverter.convert(form);
    }
}
