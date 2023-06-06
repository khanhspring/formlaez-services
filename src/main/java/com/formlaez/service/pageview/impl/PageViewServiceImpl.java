package com.formlaez.service.pageview.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.application.model.request.SearchFormSubmissionRequest;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.PageViewConverter;
import com.formlaez.infrastructure.converter.PageViewDataConverter;
import com.formlaez.infrastructure.enumeration.PageViewStatus;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.infrastructure.repository.JpaPageViewRepository;
import com.formlaez.service.pageview.PageViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PageViewServiceImpl implements PageViewService {

    private final JpaPageViewRepository jpaPageViewRepository;
    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final PageViewConverter pageViewConverter;
    private final PageViewDataConverter pageViewDataConverter;

    @Override
    @Transactional(readOnly = true)
    public PageViewResponse findByCode(String code) {
        return jpaPageViewRepository.findByCodeAndStatus(code, PageViewStatus.Published)
                .map(pageViewConverter::convert)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JsonNode> getPageViewData(String pageViewCode, Pageable pageable) {
        var pageView = jpaPageViewRepository.findByCodeAndStatus(pageViewCode, PageViewStatus.Published)
                .orElseThrow(ResourceNotFoundException::new);
        var form = pageView.getForm();
        var searchSubmission = SearchFormSubmissionRequest.builder()
                .formCode(form.getCode())
                .build();
        return jpaFormSubmissionRepository.search(searchSubmission, pageable)
                .map(submission -> pageViewDataConverter.convert(submission.getFormSubmission()));
    }

}
