package com.formlaez.infrastructure.converter;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.formlaez.infrastructure.enumeration.FormSectionType;
import com.formlaez.infrastructure.model.common.csv.CsvHeader;
import com.formlaez.infrastructure.model.common.csv.CsvInfo;
import com.formlaez.infrastructure.model.common.csv.CsvRecord;
import com.formlaez.infrastructure.model.common.csv.CsvRecordItem;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormPage;
import com.formlaez.infrastructure.model.entity.form.JpaFormSection;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FormSubmissionDataCsvConverter {

    private final FormFieldValueConverter formFieldValueConverter;

    @Transactional(readOnly = true)
    public CsvInfo convert(JpaForm form, List<JpaFormSubmission> submissions) {
        var headers = processHeaders(form);
        var records = processRecords(headers, submissions);
        return CsvInfo.builder()
                .headers(headers)
                .records(records)
                .build();
    }

    private List<CsvHeader> processHeaders(JpaForm form) {
        List<CsvHeader> headers = new ArrayList<>();
        if (ObjectUtils.isEmpty(form.getPages())) {
            return headers;
        }
        for (var page : form.getPages()) {
            processHeaders(page, headers);
        }
        return headers;
    }

    private void processHeaders(JpaFormPage page, List<CsvHeader> headers) {
        if (ObjectUtils.isEmpty(page.getSections())) {
            return;
        }
        for (var section : page.getSections()) {
            processHeaders(section, headers);
        }
    }

    private void processHeaders(JpaFormSection section, List<CsvHeader> headers) {
        if (ObjectUtils.isEmpty(section.getFields())) {
            return;
        }

        if (section.getType() == FormSectionType.Single) {
            var field = section.getFields().get(0);
            if (!field.getType().isFormControl()) {
                return;
            }

            var header = CsvHeader.builder()
                    .id(field.getCode())
                    .title(field.getTitle())
                    .field(field)
                    .build();
            headers.add(header);
            return;
        }
        if (section.getType() == FormSectionType.Table) {
            throw new IllegalArgumentException("Section table is not supported");
        }

        var header = CsvHeader.builder()
                .id(section.getCode())
                .title(section.getTitle())
                .repeat(1)
                .build();

        for (var field : section.getFields()) {
            if (!field.getType().isFormControl()) {
                continue;
            }
            var nestedHeader = CsvHeader.builder()
                    .id(field.getCode())
                    .title(field.getTitle())
                    .field(field)
                    .build();
            header.addChildren(nestedHeader);
        }
        headers.add(header);
    }

    private List<CsvRecord> processRecords(List<CsvHeader> headers, List<JpaFormSubmission> submissions) {
        if (ObjectUtils.isEmpty(submissions)) {
            return Collections.emptyList();
        }
        List<CsvRecord> records = new ArrayList<>();
        for (var submission : submissions) {
            var row = processRecord(headers, submission);
            if (Objects.nonNull(row)) {
                records.add(row);
            }
        }
        return records;
    }

    private CsvRecord processRecord(List<CsvHeader> headers, JpaFormSubmission submission) {
        var data = submission.getData();
        if (Objects.isNull(data)) {
            return null;
        }
        var row = new CsvRecord();
        for (var header : headers) {

            if (!header.hasChildren()) {
                String value = formFieldValueConverter.asTextValue(header.getField(), data, "");
                var recordItem = CsvRecordItem.builder()
                        .headerId(header.getId())
                        .values(List.of(value))
                        .build();

                row.addItem(recordItem);
                continue;
            }
            var rawData = data.get(header.getId());
            if (rawData instanceof ArrayNode dataArray) {
                header.setMaxRepeat(dataArray.size());
                List<String> values = new ArrayList<>();

                for (var dataItem : dataArray) {
                    for (var nestedHeader : header.getChildren()) {
                        var itemRawValue = dataItem.get(nestedHeader.getId());
                        String value = formFieldValueConverter.asTextValue(nestedHeader.getField(), itemRawValue, "");
                        values.add(value);
                    }
                }

                var recordItem = CsvRecordItem.builder()
                        .headerId(header.getId())
                        .values(values)
                        .build();
                row.addItem(recordItem);
            }

        }
        return row;
    }
}
