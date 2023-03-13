package com.formlaez.service.admin.form.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.formlaez.application.model.request.CreateFormDocumentTemplateRequest;
import com.formlaez.application.model.request.SearchFormDocumentTemplateRequest;
import com.formlaez.application.model.request.UpdateFormDocumentTemplateRequest;
import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.model.common.attachment.AttachmentMetadata;
import com.formlaez.infrastructure.model.entity.JpaAttachment;
import com.formlaez.infrastructure.model.entity.JpaFormDocumentTemplate;
import com.formlaez.infrastructure.repository.JpaAttachmentRepository;
import com.formlaez.infrastructure.repository.JpaFormDocumentTemplateRepository;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.util.AttachmentUtils;
import com.formlaez.service.admin.form.FormDocumentTemplateService;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;

import static com.formlaez.infrastructure.enumeration.AttachmentCategory.DocumentTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormDocumentTemplateServiceImpl implements FormDocumentTemplateService {

    private static final String ACCEPTED_EXTENSIONS = "docx";

    private final JpaFormDocumentTemplateRepository jpaFormDocumentTemplateRepository;
    private final JpaAttachmentRepository jpaAttachmentRepository;
    private final JpaFormRepository jpaFormRepository;
    private final CloudStorageService cloudStorageService;

    @Override
    @Transactional(readOnly = true)
    public Page<FormDocumentTemplateResponse> search(SearchFormDocumentTemplateRequest request, Pageable pageable) {
        return jpaFormDocumentTemplateRepository.search(request, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional
    public Long create(CreateFormDocumentTemplateRequest request) {
        var form = jpaFormRepository.findById(request.getFormId())
                .orElseThrow(InvalidParamsException::new);

        var file = request.getFile();
        var originalName = file.getOriginalFilename();
        var extension = FilenameUtils.getExtension(originalName);

        Assert.notNull(extension, "File extension is invalid");
        Assert.isTrue(extension.toLowerCase().matches(ACCEPTED_EXTENSIONS), "File extension is not supported");

        var attachmentCode = NanoIdUtils.randomNanoId();
        var fileName = String.format("%s.%s", attachmentCode, extension);
        var cloudStorageLocation = AttachmentUtils.documentTemplateLocation(form.getWorkspace().getCode(), form.getCode());

        var attachment = JpaAttachment.builder()
                .code(attachmentCode)
                .name(fileName)
                .originalName(originalName)
                .extension(extension)
                .size(file.getSize())
                .type(file.getContentType())
                .cloudStorageLocation(cloudStorageLocation)
                .category(DocumentTemplate)
                .workspace(form.getWorkspace())
                .build();
        attachment = jpaAttachmentRepository.save(attachment);

        var documentTemplate = JpaFormDocumentTemplate.builder()
                .code(NanoIdUtils.randomNanoId())
                .title(request.getTitle())
                .description(request.getDescription())
                .attachment(attachment)
                .form(form)
                .build();
        documentTemplate = jpaFormDocumentTemplateRepository.save(documentTemplate);

        var attachmentMetadata = AttachmentMetadata.builder()
                .fileName(fileName)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .cloudStorageLocation(cloudStorageLocation)
                .build();
        try {
            cloudStorageService.upload(file.getInputStream(), attachmentMetadata);
        } catch (IOException e) {
            log.error("Error when upload document template to cloud storage", e);
            throw new ApplicationException(e);
        }

        return documentTemplate.getId();
    }

    @Override
    @Transactional
    public void update(UpdateFormDocumentTemplateRequest request) {
        var documentTemplate = jpaFormDocumentTemplateRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);
        documentTemplate.setDescription(request.getDescription());
        documentTemplate.setTitle(request.getTitle());
        jpaFormDocumentTemplateRepository.save(documentTemplate);
    }

    private FormDocumentTemplateResponse toResponse(JpaFormDocumentTemplate source) {
        return FormDocumentTemplateResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .attachmentCode(source.getAttachment().getCode())
                .title(source.getTitle())
                .description(source.getDescription())
                .extension(source.getAttachment().getExtension())
                .size(source.getAttachment().getSize())
                .originalName(source.getAttachment().getOriginalName())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .build();
    }
}
