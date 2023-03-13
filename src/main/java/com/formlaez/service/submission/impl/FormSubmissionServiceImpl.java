package com.formlaez.service.submission.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.FormSubmissionStatus;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.service.submission.FormSubmissionService;
import com.formlaez.service.share.FormSubmissionSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormSubmissionServiceImpl implements FormSubmissionService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormSubmissionSnapshotService formSubmissionSnapshotService;

    @Override
    @Transactional
    public String create(CreateFormSubmissionRequest request) {
        var form = jpaFormRepository.findByCode(request.getFormCode())
                .orElseThrow(InvalidParamsException::new);

        var submission = JpaFormSubmission.builder()
                .code(NanoIdUtils.randomNanoId())
                .form(form)
                .data(request.getData())
                .status(FormSubmissionStatus.Active)
                .build();
        submission = jpaFormSubmissionRepository.save(submission);
        formSubmissionSnapshotService.takeSnapshot(submission.getId());
        return submission.getCode();
    }
}
