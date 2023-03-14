package com.formlaez.service.form.impl;

import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.converter.FormDocumentTemplateResponseConverter;
import com.formlaez.infrastructure.repository.JpaFormDocumentTemplateRepository;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.service.form.FormDocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormDocumentTemplateServiceImpl implements FormDocumentTemplateService {

    private final JpaFormDocumentTemplateRepository jpaFormDocumentTemplateRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormDocumentTemplateResponseConverter formDocumentTemplateResponseConverter;

    @Override
    @Transactional(readOnly = true)
    public List<FormDocumentTemplateResponse> getByFormId(Long formId) {
        var form = jpaFormRepository.findById(formId)
                .orElseThrow(InvalidParamsException::new);

        if (!form.isAllowPrinting()) {
            return Collections.emptyList();
        }

        return jpaFormDocumentTemplateRepository.findAllByFormIdOrderByCreatedDateDesc(formId)
                .stream()
                .map(formDocumentTemplateResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
