package com.formlaez.infrastructure.model.common.csv;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvInfo {
    private List<CsvHeader> headers;
    private List<CsvRecord> records;

    public List<String> headerNames() {
        if (ObjectUtils.isEmpty(headers)) {
            return Collections.emptyList();
        }
        return headers.stream()
                .map(CsvHeader::extract)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<List<String>> recordValues() {
        if (ObjectUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        if (ObjectUtils.isEmpty(headers)) {
            return Collections.emptyList();
        }
        Map<String, CsvHeader> headerMap = headers.stream()
                        .collect(Collectors.toMap(CsvHeader::getId, item -> item));
        return records.stream()
                .map(item -> item.extract(headerMap))
                .collect(Collectors.toList());
    }
}
