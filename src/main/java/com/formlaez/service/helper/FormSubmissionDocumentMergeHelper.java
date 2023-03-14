package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.converter.FormSubmissionDataJsonConverter;
import com.formlaez.infrastructure.docengine.DocumentProcessor;
import com.formlaez.infrastructure.docengine.variable.Variable;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.repository.JpaFormDocumentTemplateRepository;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

@Component
@RequiredArgsConstructor
public class FormSubmissionDocumentMergeHelper {

    private final JpaFormDocumentTemplateRepository jpaFormDocumentTemplateRepository;
    private final CloudStorageService cloudStorageService;
    private final FormSubmissionDataJsonConverter formSubmissionDataJsonConverter;

    @Transactional(readOnly = true)
    public byte[] mergeDocument(JpaFormSubmission submission, Long templateId) {
        var documentTemplate = jpaFormDocumentTemplateRepository.findById(templateId)
                .orElseThrow(InvalidParamsException::new);

        var documentTemplateInputStream = cloudStorageService.download(
                documentTemplate.getAttachment().getCloudStorageLocation(),
                documentTemplate.getAttachment().getName()
        );

        var json = formSubmissionDataJsonConverter.convert(submission);
        var documentProcessor = new DocumentProcessor();
        var outputStream = new ByteArrayOutputStream();
        documentProcessor.process(documentTemplateInputStream, outputStream, Variable.of(json));
        return outputStream.toByteArray();
    }
}
