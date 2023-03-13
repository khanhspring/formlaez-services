package com.formlaez.service.admin.form.impl;

import com.formlaez.application.model.request.CreateFormSectionRequest;
import com.formlaez.application.model.request.MoveFormSectionRequest;
import com.formlaez.application.model.request.UpdateFormSectionRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import com.formlaez.infrastructure.model.entity.form.JpaFormSection;
import com.formlaez.infrastructure.repository.JpaFormFieldOptionRepository;
import com.formlaez.infrastructure.repository.JpaFormFieldRepository;
import com.formlaez.infrastructure.repository.JpaFormPageRepository;
import com.formlaez.infrastructure.repository.JpaFormSectionRepository;
import com.formlaez.service.admin.form.FormSectionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormSectionAdminServiceImpl implements FormSectionAdminService {

    private final JpaFormSectionRepository jpaFormSectionRepository;
    private final JpaFormPageRepository jpaFormPageRepository;
    private final JpaFormFieldRepository jpaFormFieldRepository;
    private final JpaFormFieldOptionRepository jpaFormFieldOptionRepository;

    @Override
    @Transactional
    public Long create(CreateFormSectionRequest request) {
        var formPage = jpaFormPageRepository.findById(request.getPageId())
                .orElseThrow(InvalidParamsException::new);

        var form = formPage.getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

        jpaFormSectionRepository.changePositionInRange(
                formPage.getId(),
                1,
                request.getPosition(),
                null
        );

        var section = JpaFormSection.builder()
                .title(Objects.requireNonNullElse(request.getTitle(), "Untitled"))
                .code(request.getCode())
                .description(request.getDescription())
                .variableName(request.getVariableName())
                .type(request.getType())
                .position(request.getPosition())
                .repeatable(request.isRepeatable())
                .repeatButtonLabel(request.getRepeatButtonLabel())
                .page(formPage)
                .build();

        var sectionSaved = jpaFormSectionRepository.save(section);

        if (!ObjectUtils.isEmpty(request.getFields())) {
            request.getFields().forEach(field -> {
                var jpaField = JpaFormField.builder()
                        .code(field.getCode())
                        .variableName(field.getVariableName())
                        .title(Objects.requireNonNullElse(field.getTitle(), "Untitled"))
                        .description(field.getDescription())
                        .placeholder(field.getPlaceholder())
                        .type(field.getType())
                        .required(field.isRequired())
                        .position(field.getPosition())
                        .section(sectionSaved)
                        .build();
                jpaFormFieldRepository.save(jpaField);
            });
        }
        return sectionSaved.getId();
    }

    @Override
    public void update(UpdateFormSectionRequest request) {
        var section = jpaFormSectionRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);

        var form = section.getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

        section.setTitle(request.getTitle());
        section.setDescription(request.getDescription());
        section.setRepeatButtonLabel(request.getRepeatButtonLabel());
        section.setVariableName(request.getVariableName());
        jpaFormSectionRepository.save(section);
    }

    @Override
    @Transactional
    public void move(MoveFormSectionRequest request) {
        var section = jpaFormSectionRepository.findByCode(request.getSectionCode())
                .orElseThrow(ResourceNotFoundException::new);

        var form = section.getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

        if (request.getNewPosition() == section.getPosition()) {
            return;
        }

        if (request.getNewPosition() > section.getPosition()) {
            jpaFormSectionRepository.changePositionInRange(
                    section.getPage().getId(),
                    -1,
                    section.getPosition() + 1,
                    request.getNewPosition()
            );
            section.setPosition(request.getNewPosition());
            jpaFormSectionRepository.save(section);
            return;
        }

        jpaFormSectionRepository.changePositionInRange(
                section.getPage().getId(),
                1,
                request.getNewPosition(),
                section.getPosition() - 1
        );
        section.setPosition(request.getNewPosition());
        jpaFormSectionRepository.save(section);
    }

    @Override
    @Transactional
    public void remove(String code) {
        var section = jpaFormSectionRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);

        var form = section.getPage().getForm();
        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");
        Assert.isTrue(form.getStatus() != FormStatus.Deleted, "Form not found");

        for (var field : section.getFields()) {
            jpaFormFieldOptionRepository.deleteAllByFieldId(field.getId());
        }
        jpaFormFieldRepository.deleteAllBySectionId(section.getId());

        jpaFormSectionRepository.changePositionInRange(
                section.getPage().getId(),
                -1,
                section.getPosition() + 1,
                null
        );
        jpaFormSectionRepository.delete(section);
    }
}
