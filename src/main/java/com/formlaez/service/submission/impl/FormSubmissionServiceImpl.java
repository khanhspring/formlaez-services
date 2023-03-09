package com.formlaez.service.submission.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.formlaez.application.model.request.AdvanceSearchFormSubmissionRequest;
import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.SearchFormSubmissionRequest;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.converter.UserResponseConvertor;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.model.projection.JpaFormSubmissionProjection;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.infrastructure.repository.custom.JpaFormSubmissionSearchResult;
import com.formlaez.infrastructure.util.StringUtils;
import com.formlaez.service.submission.FormSubmissionService;
import com.formlaez.service.submission.FormSubmissionSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormSubmissionServiceImpl implements FormSubmissionService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormRepository jpaFormRepository;
    private final FormSubmissionSnapshotService formSubmissionSnapshotService;
    private final UserResponseConvertor userResponseConvertor;

    @Override
    @Transactional
    public String create(CreateFormSubmissionRequest request) {
        var form = jpaFormRepository.findByCode(request.getFormCode())
                .orElseThrow(InvalidParamsException::new);

        var submission = JpaFormSubmission.builder()
                .code(NanoIdUtils.randomNanoId())
                .form(form)
                .data(request.getData())
                .build();
        submission = jpaFormSubmissionRepository.save(submission);
        formSubmissionSnapshotService.takeSnapshot(submission.getId());
        return submission.getCode();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormSubmissionResponse> search(SearchFormSubmissionRequest request, Pageable pageable) {
        return jpaFormSubmissionRepository.search(request, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<FormSubmissionResponse> searchAdvance(AdvanceSearchFormSubmissionRequest request, Pageable pageable) {
        return jpaFormSubmissionRepository.searchAdvance(request, pageable)
                .map(this::toResponse);
    }

    private FormSubmissionResponse toResponse(JpaFormSubmissionProjection source) {
        return FormSubmissionResponse.builder()
                .id(source.getFormSubmission().getId())
                .code(source.getFormSubmission().getCode())
                .data(source.getFormSubmission().getData())
                .createdDate(source.getFormSubmission().getCreatedDate())
                .lastModifiedDate(source.getFormSubmission().getLastModifiedDate())
                .lastModifiedBy(userResponseConvertor.convert(source.getLastModifiedBy()))
                .createdBy(userResponseConvertor.convert(source.getCreatedBy()))
                .build();
    }

    private FormSubmissionResponse toResponse(JpaFormSubmissionSearchResult source) {
        return FormSubmissionResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .data(source.getData())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .lastModifiedBy(userResponseConvertor.convert(source.getLastModifiedBy()))
                .createdBy(userResponseConvertor.convert(source.getCreatedBy()))
                .build();
    }
}
