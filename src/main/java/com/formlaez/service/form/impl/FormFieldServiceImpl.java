package com.formlaez.service.form.impl;

import com.formlaez.application.model.request.CreateFormFieldRequest;
import com.formlaez.application.model.request.MoveFormFieldRequest;
import com.formlaez.application.model.request.UpdateFormFieldRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.repository.JpaFormFieldRepository;
import com.formlaez.infrastructure.repository.JpaFormSectionRepository;
import com.formlaez.service.form.FormFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormFieldServiceImpl implements FormFieldService {

    private final JpaFormFieldRepository jpaFormFieldRepository;
    private final JpaFormSectionRepository jpaFormSectionRepository;

    @Override
    @Transactional
    public Long create(CreateFormFieldRequest request) {
        Assert.hasText(request.getSectionCode(), "Section code must not be blank");
        var section = jpaFormSectionRepository.findByCode(request.getSectionCode())
                .orElseThrow(InvalidParamsException::new);

        jpaFormFieldRepository.changePositionInRange(
                section.getId(),
                1,
                request.getPosition(),
                null
        );

        var field = JpaFormField.builder()
                .code(request.getCode())
                .variableName(request.getVariableName())
                .title(Objects.requireNonNullElse(request.getTitle(), "Untitled"))
                .description(request.getDescription())
                .placeholder(request.getPlaceholder())
                .type(request.getType())
                .required(request.isRequired())
                .position(request.getPosition())
                .section(section)
                .build();
        return jpaFormFieldRepository.save(field).getId();
    }

    @Override
    @Transactional
    public void update(UpdateFormFieldRequest request) {
        var field = jpaFormFieldRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);
        field.setVariableName(request.getVariableName());
        field.setContent(request.getContent());
        field.setCaption(request.getCaption());
        field.setDescription(request.getDescription());
        field.setHideTitle(request.isHideTitle());
        field.setAcceptedDomains(request.getAcceptedDomains());
        field.setMax(request.getMax());
        field.setMin(request.getMin());
        field.setMaxLength(request.getMaxLength());
        field.setMinLength(request.getMinLength());
        field.setMultipleSelection(request.isMultipleSelection());
        field.setReadonly(request.isReadonly());
        field.setPlaceholder(request.getPlaceholder());
        field.setRequired(request.isRequired());
        field.setShowTime(request.isShowTime());
        field.setUrl(request.getUrl());
        field.setTitle(Objects.requireNonNullElse(request.getTitle(), "Untitled"));
        jpaFormFieldRepository.save(field);
    }

    @Override
    @Transactional
    public void move(MoveFormFieldRequest request) {
        var field = jpaFormFieldRepository.findByCode(request.getFieldCode())
                .orElseThrow(InvalidParamsException::new);

        if (request.getNewPosition() == field.getPosition()) {
            return;
        }

        if (request.getNewPosition() > field.getPosition()) {
            jpaFormFieldRepository.changePositionInRange(
                    field.getSection().getId(),
                    -1,
                    field.getPosition() + 1,
                    request.getNewPosition()
            );
            field.setPosition(request.getNewPosition());
            jpaFormFieldRepository.save(field);
            return;
        }

        jpaFormFieldRepository.changePositionInRange(
                field.getSection().getId(),
                1,
                request.getNewPosition(),
                field.getPosition() - 1
        );
        field.setPosition(request.getNewPosition());
        jpaFormFieldRepository.save(field);
    }
}
