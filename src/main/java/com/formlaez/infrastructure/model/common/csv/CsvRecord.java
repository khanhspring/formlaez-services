package com.formlaez.infrastructure.model.common.csv;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvRecord {
    private List<CsvRecordItem> items;

    public void addItem(CsvRecordItem item) {
        if (Objects.isNull(items)) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public List<String> extract(Map<String, CsvHeader> headerMap) {
        List<String> results = new ArrayList<>();
        for (var item : items) {
            var header = headerMap.get(item.getHeaderId());
            int totalItems = 1;
            if (header.hasChildren()) {
                var repeat = Objects.requireNonNullElse(header.getRepeat(), 1);
                totalItems = repeat * header.getChildren().size();
            }
            List<String> itemResults = item.extract(totalItems);
            results.addAll(itemResults);
        }
        return results;
    }
}
