package com.formlaez.service.helper;

import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.converter.FormSubmissionDataJsonConverter;
import com.formlaez.infrastructure.docengine.DocumentProcessor;
import com.formlaez.infrastructure.docengine.variable.Variable;
import com.formlaez.infrastructure.enumeration.DocumentMergeFileType;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.repository.JpaFormDocumentTemplateRepository;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormSubmissionDocumentMergeHelper {

    private final JpaFormDocumentTemplateRepository jpaFormDocumentTemplateRepository;
    private final CloudStorageService cloudStorageService;
    private final FormSubmissionDataJsonConverter formSubmissionDataJsonConverter;

    @Transactional(readOnly = true)
    public byte[] mergeDocument(JpaFormSubmission submission, Long templateId) {
        return mergeDocument(submission, templateId, DocumentMergeFileType.Docx);
    }

    @Transactional(readOnly = true)
    public byte[] mergeDocument(JpaFormSubmission submission, Long templateId, DocumentMergeFileType fileType) {
        var documentTemplate = jpaFormDocumentTemplateRepository.findById(templateId)
                .orElseThrow(InvalidParamsException::new);

        var documentTemplateInputStream = cloudStorageService.download(
                documentTemplate.getAttachment().getCloudStorageLocation(),
                documentTemplate.getAttachment().getName()
        );

        var json = formSubmissionDataJsonConverter.convert(submission);
        var documentProcessor = new DocumentProcessor();

        if (fileType == DocumentMergeFileType.Docx) {
            var outputStream = documentProcessor.processToByteArray(documentTemplateInputStream, Variable.of(json));
            return outputStream.toByteArray();
        }

        if (fileType == DocumentMergeFileType.Pdf) {
            try {
                var documentResult = documentProcessor.processAndGetDocument(documentTemplateInputStream, Variable.of(json));
                var options = PdfOptions.create();
                var outputStream = new ByteArrayOutputStream();
                PdfConverter.getInstance().convert(documentResult, outputStream, options);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
        }

        throw new ApplicationException("Not supported this file type");
    }
}
