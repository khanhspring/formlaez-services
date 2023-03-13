package com.formlaez.infrastructure.model.common.csv;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvRecordItem {
    private String headerId;
    private List<String> values;

    public List<String> extract(int length) {
        if (length < 1) {
            return Collections.emptyList();
        }
        if (ObjectUtils.isEmpty(values)) {
            values = new ArrayList<>();
        }
        List<String> results = new ArrayList<>(values);

        var totalExpandItems = length - values.size();
        for (var i = 0; i < totalExpandItems; i++) {
            results.add(null);
        }
        return results;
    }
}
