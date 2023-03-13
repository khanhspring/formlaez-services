package com.formlaez.service.admin.submission.impl;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.FormSubmissionDataCsvConverter;
import com.formlaez.infrastructure.converter.FormSubmissionDataJsonConverter;
import com.formlaez.infrastructure.converter.UserResponseConverter;
import com.formlaez.infrastructure.docengine.DocumentProcessor;
import com.formlaez.infrastructure.docengine.variable.Variable;
import com.formlaez.infrastructure.enumeration.FormSubmissionStatus;
import com.formlaez.infrastructure.model.projection.JpaFormSubmissionProjection;
import com.formlaez.infrastructure.repository.JpaFormDocumentTemplateRepository;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaFormSubmissionRepository;
import com.formlaez.infrastructure.repository.custom.JpaFormSubmissionSearchResult;
import com.formlaez.infrastructure.util.CsvUtils;
import com.formlaez.service.admin.submission.FormSubmissionAdminService;
import com.formlaez.service.share.FormSubmissionSnapshotService;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormSubmissionAdminServiceImpl implements FormSubmissionAdminService {

    private final JpaFormSubmissionRepository jpaFormSubmissionRepository;
    private final JpaFormRepository jpaFormRepository;
    private final JpaFormDocumentTemplateRepository jpaFormDocumentTemplateRepository;
    private final CloudStorageService cloudStorageService;
    private final UserResponseConverter userResponseConverter;
    private final FormSubmissionSnapshotService formSubmissionSnapshotService;
    private final FormSubmissionDataJsonConverter formSubmissionDataJsonConverter;
    private final FormSubmissionDataCsvConverter formSubmissionDataCsvConverter;

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

    @Override
    @Transactional
    public void update(UpdateFormSubmissionRequest request) {
        var submission = jpaFormSubmissionRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);
        submission.setData(request.getData());
        jpaFormSubmissionRepository.save(submission);
        formSubmissionSnapshotService.takeSnapshot(submission.getId());
    }

    @Override
    public void archive(String submissionCode) {
        var submission = jpaFormSubmissionRepository.findByCode(submissionCode)
                .orElseThrow(ResourceNotFoundException::new);
        submission.setStatus(FormSubmissionStatus.Archived);
        jpaFormSubmissionRepository.save(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] mergeDocument(MergeDocumentFormSubmissionRequest request) {
        var submission = jpaFormSubmissionRepository.findByCode(request.getCode())
                .orElseThrow(ResourceNotFoundException::new);

        var documentTemplate = jpaFormDocumentTemplateRepository.findById(request.getTemplateId())
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

    @Override
    public void export(ExportFormSubmissionRequest request, PrintWriter writer) {
        var form = jpaFormRepository.findByCode(request.getFormCode())
                .orElseThrow(InvalidParamsException::new);

        var searchRequest = SearchFormSubmissionRequest.builder()
                .formCode(request.getFormCode())
                .build();
        var searchResults = jpaFormSubmissionRepository.search(searchRequest, Pageable.unpaged());
        var submissions = searchResults.stream()
                .map(JpaFormSubmissionProjection::getFormSubmission)
                .collect(Collectors.toList());

        var csvInfo = formSubmissionDataCsvConverter.convert(form, submissions);
        var headerNames = csvInfo.headerNames();
        if (headerNames.size() > 0) {
            headerNames.set(0, CsvUtils.UTF8_BOM + headerNames.get(0));
        }
        var headerNameArr = headerNames.toArray(new String[]{});

        var csvFormat = CSVFormat.Builder.create()
                .setHeader(headerNameArr)
                .build();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
            var recordValues = csvInfo.recordValues();
            for (var record : recordValues) {
                csvPrinter.printRecord(record);
            }
            csvPrinter.flush();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private FormSubmissionResponse toResponse(JpaFormSubmissionProjection source) {
        return FormSubmissionResponse.builder()
                .id(source.getFormSubmission().getId())
                .code(source.getFormSubmission().getCode())
                .data(source.getFormSubmission().getData())
                .createdDate(source.getFormSubmission().getCreatedDate())
                .lastModifiedDate(source.getFormSubmission().getLastModifiedDate())
                .lastModifiedBy(userResponseConverter.convert(source.getLastModifiedBy()))
                .createdBy(userResponseConverter.convert(source.getCreatedBy()))
                .build();
    }

    private FormSubmissionResponse toResponse(JpaFormSubmissionSearchResult source) {
        return FormSubmissionResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .data(source.getData())
                .createdDate(source.getCreatedDate())
                .lastModifiedDate(source.getLastModifiedDate())
                .lastModifiedBy(userResponseConverter.convert(source.getLastModifiedBy()))
                .createdBy(userResponseConverter.convert(source.getCreatedBy()))
                .build();
    }
}
