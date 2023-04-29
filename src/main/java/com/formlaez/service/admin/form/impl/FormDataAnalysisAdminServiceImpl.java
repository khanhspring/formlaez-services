package com.formlaez.service.admin.form.impl;

import com.formlaez.application.model.response.form.analysis.FormDataAnalysisResponse;
import com.formlaez.application.model.response.form.analysis.FormFieldAnalysisResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.FormSectionType;
import com.formlaez.infrastructure.enumeration.FormSubmissionStatus;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.service.admin.form.FormDataAnalysisAdminService;
import com.formlaez.service.helper.analysis.FormDataAnalysisHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormDataAnalysisAdminServiceImpl implements FormDataAnalysisAdminService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormDataAnalysisHelper formDataAnalysisHelper;

    @Override
    @Transactional(readOnly = true)
    public FormDataAnalysisResponse getAnalysis(String code) {
        var form = jpaFormRepository.findByCode(code)
                        .orElseThrow(ResourceNotFoundException::new);

        List<FormFieldAnalysisResponse> items = new ArrayList<>();
        for (var page : form.getPages()) {
            for (var section : page.getSections()) {
                if (section.getType() == FormSectionType.Single) {
                    // only support for Single fields for now
                    for (var field : section.getFields()) {
                        var item = formDataAnalysisHelper.analyze(form, field);
                        if (Objects.nonNull(item)) {
                            items.add(item);
                        }
                    }
                }
            }
        }

        long count = jpaFormSubmissionRepository.countByFormIdAndStatus(form.getId(), FormSubmissionStatus.Active);

        return FormDataAnalysisResponse.builder()
                .items(items)
                .count(count)
                .build();
    }
}
