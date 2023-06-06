package com.formlaez.service.admin.pageview.impl;

import com.formlaez.application.model.request.pageview.CreatePageViewRequest;
import com.formlaez.application.model.request.pageview.UpdatePageViewRequest;
import com.formlaez.application.model.response.pageview.PageViewResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.PageViewConverter;
import com.formlaez.infrastructure.enumeration.PageViewStatus;
import com.formlaez.infrastructure.model.entity.pageview.JpaPageView;
import com.formlaez.infrastructure.model.entity.pageview.JpaPageViewDetail;
import com.formlaez.infrastructure.model.entity.pageview.JpaPageViewListingField;
import com.formlaez.infrastructure.repository.*;
import com.formlaez.service.admin.pageview.PageViewAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.formlaez.infrastructure.enumeration.PageViewStatus.*;

@Service
@RequiredArgsConstructor
public class PageViewAdminServiceImpl implements PageViewAdminService {

    private final JpaPageViewRepository jpaPageViewRepository;
    private final JpaPageViewTemplateRepository jpaPageViewTemplateRepository;
    private final JpaFormRepository jpaFormRepository;
    private final JpaPageViewDetailRepository jpaPageViewDetailRepository;
    private final JpaPageViewListingFieldRepository jpaPageViewListingFieldRepository;
    private final PageViewConverter pageViewConverter;


    @Override
    @Transactional(readOnly = true)
    public List<PageViewResponse> findAllByForm(Long formId) {
        return jpaPageViewRepository.findAllByFormIdAndStatusInOrderByCreatedDateDesc(formId, List.of(Published, Draft, Unpublished))
                .stream()
                .map(pageViewConverter::convert)
                .toList();
    }

    @Override
    @Transactional
    public Long create(CreatePageViewRequest request) {
        var template = jpaPageViewTemplateRepository.findByCode(request.getTemplateCode()).orElseThrow();
        var form = jpaFormRepository.findById(request.getFormId()).orElseThrow();

        var pageView = JpaPageView.builder()
                .code(request.getCode())
                .status(Draft)
                .title(request.getTitle())
                .description(request.getDescription())
                .template(template)
                .form(form)
                .build();
        pageView = jpaPageViewRepository.save(pageView);

        var detail = JpaPageViewDetail.builder()
                .type(request.getDetail().getType())
                .customContent(request.getDetail().getCustomContent())
                .redirectType(request.getDetail().getRedirectType())
                .redirectUrl(request.getDetail().getRedirectUrl())
                .pageView(pageView)
                .build();
        pageView.setDetail(detail);
        jpaPageViewDetailRepository.save(detail);

        List<JpaPageViewListingField> listingFields = new ArrayList<>();
        for (var requestField : request.getListingFields()) {
            var field = JpaPageViewListingField.builder()
                    .fieldCode(requestField.getFieldCode())
                    .targetFieldCode(requestField.getTargetFieldCode())
                    .fixedValue(requestField.getFixedValue())
                    .type(requestField.getType())
                    .pageView(pageView)
                    .build();
            listingFields.add(field);
        }
        jpaPageViewListingFieldRepository.saveAll(listingFields);

        return pageView.getId();
    }

    @Override
    @Transactional
    public void update(UpdatePageViewRequest request) {
        var pageView = jpaPageViewRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        pageView.setTitle(request.getTitle());
        pageView.setDescription(request.getDescription());
        pageView.setCode(request.getCode());
        jpaPageViewRepository.save(pageView);

        jpaPageViewDetailRepository.deleteByPageViewId(pageView.getId());
        var detail = JpaPageViewDetail.builder()
                .type(request.getDetail().getType())
                .customContent(request.getDetail().getCustomContent())
                .redirectType(request.getDetail().getRedirectType())
                .redirectUrl(request.getDetail().getRedirectUrl())
                .pageView(pageView)
                .build();
        pageView.setDetail(detail);
        jpaPageViewDetailRepository.save(detail);

        jpaPageViewListingFieldRepository.deleteByPageViewId(pageView.getId());
        List<JpaPageViewListingField> listingFields = new ArrayList<>();
        for (var requestField : request.getListingFields()) {
            var field = JpaPageViewListingField.builder()
                    .fieldCode(requestField.getFieldCode())
                    .targetFieldCode(requestField.getTargetFieldCode())
                    .fixedValue(requestField.getFixedValue())
                    .type(requestField.getType())
                    .pageView(pageView)
                    .build();
            listingFields.add(field);
        }
        jpaPageViewListingFieldRepository.saveAll(listingFields);
    }

    @Override
    @Transactional
    public void publish(Long id) {
        var pageView = jpaPageViewRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        pageView.setStatus(Published);
        jpaPageViewRepository.save(pageView);
    }

    @Override
    @Transactional
    public void unPublish(Long id) {
        var pageView = jpaPageViewRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        pageView.setStatus(Unpublished);
        jpaPageViewRepository.save(pageView);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var pageView = jpaPageViewRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        jpaPageViewDetailRepository.deleteByPageViewId(pageView.getId());
        jpaPageViewListingFieldRepository.deleteByPageViewId(pageView.getId());
        jpaPageViewRepository.delete(pageView);
    }
}
