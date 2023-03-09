package com.formlaez.service.form.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.FormResponseConvertor;
import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormPage;
import com.formlaez.infrastructure.repository.JpaFormPageRepository;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.infrastructure.util.StringUtils;
import com.formlaez.service.form.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final JpaFormRepository jpaFormRepository;
    private final JpaFormPageRepository jpaFormPageRepository;
    private final FormResponseConvertor formResponseConvertor;

    @Override
    @Transactional(readOnly = true)
    public Page<BasicFormResponse> search(SearchFormRequest request, Pageable pageable) {
        if (request.getScope() == FormScope.Private) {
            request.setCreatedBy(AuthUtils.currentUserIdOrElseThrow());
        }
        return jpaFormRepository.search(request, pageable)
                .map(this::toBasicResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public BasicFormResponse findByCode(String code) {
        var form = jpaFormRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);

        if (form.getScope() == FormScope.Private) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            if (!currentUserId.equals(form.getCreatedBy())) {
                throw new ForbiddenException();
            }
        }
        // TODO: check for Team scope
        return this.toBasicResponse(form);
    }

    @Override
    @Transactional(readOnly = true)
    public FormResponse getDetail(String code) {
        return jpaFormRepository.findByCode(code)
                .map(formResponseConvertor::convert)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public Long create(CreateFormRequest request) {
        if (request.getScope() != FormScope.Private) {
            // FIXME: remove this whenever team is supported
            throw new InvalidParamsException();
        }

        var form = JpaForm.builder()
                .code(NanoIdUtils.randomNanoId())
                .scope(request.getScope())
                .title(request.getTitle())
                .description(request.getDescription())
                .coverType(FormCoverType.None)
                .build();
        form = jpaFormRepository.save(form);

        var firstPage = JpaFormPage.builder()
                .code(NanoIdUtils.randomNanoId())
                .title("Untitled Page")
                .position(0)
                .form(form)
                .build();
        jpaFormPageRepository.save(firstPage);

        return form.getId();
    }

    @Override
    @Transactional
    public void update(UpdateFormRequest request) {
        var form = jpaFormRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);
        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());
        form.setCoverType(request.getCoverType());
        form.setCoverColor(request.getCoverColor());
        form.setCoverImageUrl(request.getCoverImageUrl());
        jpaFormRepository.save(form);
    }

    @Override
    @Transactional
    public void archive(Long id) {

    }

    private BasicFormResponse toBasicResponse(JpaForm source) {
        return BasicFormResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .code(source.getCode())
                .coverType(source.getCoverType())
                .coverColor(source.getCoverColor())
                .coverImageUrl(source.getCoverImageUrl())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .build();
    }
}
