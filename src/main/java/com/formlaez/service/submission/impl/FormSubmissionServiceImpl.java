package com.formlaez.service.submission.impl;

import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.MergeDocumentFormSubmissionRequest;
import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.DocumentMergeFileType;
import com.formlaez.infrastructure.enumeration.FormSubmissionStatus;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.helper.FormSubmissionDocumentMergeHelper;
import com.formlaez.service.share.FormSubmissionSnapshotService;
import com.formlaez.service.submission.FormSubmissionService;
import com.formlaez.service.usage.WorkspaceUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormSubmissionServiceImpl implements FormSubmissionService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormSubmissionSnapshotService formSubmissionSnapshotService;
    private final FormSubmissionDocumentMergeHelper formSubmissionDocumentMergeHelper;
    private final WorkspaceUsageService workspaceUsageService;

    @Override
    @Transactional
    public String create(CreateFormSubmissionRequest request) {
        var form = jpaFormRepository.findByCode(request.getFormCode())
                .orElseThrow(InvalidParamsException::new);

        workspaceUsageService.checkSubmissionLimitAndIncreaseOrElseThrow(form.getWorkspace().getId());
        var submission = JpaFormSubmission.builder()
                .code(RandomUtils.randomNanoId())
                .form(form)
                .data(request.getData())
                .status(FormSubmissionStatus.Active)
                .build();
        submission = jpaFormSubmissionRepository.save(submission);
        formSubmissionSnapshotService.takeSnapshot(submission.getId());
        return submission.getCode();
    }

    @Override
    public byte[] mergeDocument(MergeDocumentFormSubmissionRequest request) {
        var submission = jpaFormSubmissionRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);

        var form = submission.getForm();

        if (!form.isAllowPrinting()) {
            throw new ForbiddenException();
        }

        workspaceUsageService.checkSubmissionLimitAndIncreaseOrElseThrow(form.getWorkspace().getId());

        var fileType = Objects.requireNonNullElse(request.getFileType(), DocumentMergeFileType.Docx);
        return formSubmissionDocumentMergeHelper.mergeDocument(submission, request.getTemplateId(), fileType);
    }
}
