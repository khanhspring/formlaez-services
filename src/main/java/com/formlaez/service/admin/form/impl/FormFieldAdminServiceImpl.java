package com.formlaez.service.admin.form.impl;

import com.formlaez.application.model.request.CreateFormFieldRequest;
import com.formlaez.application.model.request.MoveFormFieldRequest;
import com.formlaez.application.model.request.UpdateFormFieldRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.model.entity.form.JpaFormFieldOption;
import com.formlaez.infrastructure.repository.JpaFormFieldOptionRepository;
import com.formlaez.infrastructure.repository.JpaFormFieldRepository;
import com.formlaez.infrastructure.repository.JpaFormSectionRepository;
import com.formlaez.service.admin.form.FormFieldAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormFieldAdminServiceImpl implements FormFieldAdminService {

    private final JpaFormFieldRepository jpaFormFieldRepository;
    private final JpaFormSectionRepository jpaFormSectionRepository;
    private final JpaFormFieldOptionRepository jpaFormFieldOptionRepository;

    @Override
    @Transactional
    public Long create(CreateFormFieldRequest request) {
        Assert.hasText(request.getSectionCode(), "Section code must not be blank");
        var section = jpaFormSectionRepository.findByCode(request.getSectionCode())
                .orElseThrow(InvalidParamsException::new);

        var form = section.getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

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
                .acceptedDomains(request.getAcceptedDomains())
                .caption(request.getCaption())
                .content(request.getContent())
                .min(request.getMin())
                .max(request.getMax())
                .minLength(request.getMinLength())
                .maxLength(request.getMaxLength())
                .hideTitle(request.isHideTitle())
                .multipleSelection(request.isMultipleSelection())
                .url(request.getUrl())
                .readonly(request.isReadonly())
                .showTime(request.isShowTime())
                .content(request.getContent())
                .position(request.getPosition())
                .section(section)
                .build();

        field = jpaFormFieldRepository.save(field);

        if (!ObjectUtils.isEmpty(request.getOptions())) {
            List<JpaFormFieldOption> options = new ArrayList<>();
            int optionPosition = 0;
            for (var option : request.getOptions()) {
                var jpaOption = JpaFormFieldOption.builder()
                        .code(option.getCode())
                        .label(option.getLabel())
                        .field(field)
                        .position(optionPosition++)
                        .build();
                options.add(jpaOption);
            }
            jpaFormFieldOptionRepository.saveAll(options);
        }
        return field.getId();
    }

    @Override
    @Transactional
    public void update(UpdateFormFieldRequest request) {
        var field = jpaFormFieldRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);

        var form = field.getSection().getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

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
        field = jpaFormFieldRepository.save(field);

        var existingOptions = field.getOptions();
        List<JpaFormFieldOption> options = new ArrayList<>();
        if (!ObjectUtils.isEmpty(request.getOptions())) {
            var optionPosition = 0;
            for (var option : request.getOptions()) {
                var existingOption = existingOptions.stream()
                        .filter(item -> item.getCode().equals(option.getCode()))
                        .findFirst();

                JpaFormFieldOption jpaOption;
                if (existingOption.isPresent()) {
                    jpaOption = existingOption.get();
                    jpaOption.setLabel(option.getLabel());
                    jpaOption.setPosition(optionPosition++);
                } else {
                    jpaOption = JpaFormFieldOption.builder()
                            .code(option.getCode())
                            .label(option.getLabel())
                            .field(field)
                            .position(optionPosition++)
                            .build();
                }
                options.add(jpaOption);
            }
            jpaFormFieldOptionRepository.saveAll(options);
        }
        var deletedOptions = existingOptions.stream()
                .filter(existing -> options.stream().noneMatch(option -> option.getCode().equals(existing.getCode())))
                .collect(Collectors.toList());
        jpaFormFieldOptionRepository.deleteAll(deletedOptions);
    }

    @Override
    @Transactional
    public void move(MoveFormFieldRequest request) {
        var field = jpaFormFieldRepository.findByCode(request.getFieldCode())
                .orElseThrow(InvalidParamsException::new);

        var form = field.getSection().getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

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

    @Override
    @Transactional
    public void remove(String code) {
        var field = jpaFormFieldRepository.findByCode(code)
                .orElseThrow(InvalidParamsException::new);

        var form = field.getSection().getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

        jpaFormFieldRepository.changePositionInRange(
                field.getSection().getId(),
                -1,
                field.getPosition() + 1,
                null
        );
        jpaFormFieldOptionRepository.deleteAllByFieldId(field.getId());
        jpaFormFieldRepository.delete(field);
    }
}
