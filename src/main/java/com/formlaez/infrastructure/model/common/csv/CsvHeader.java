package com.formlaez.infrastructure.model.common.csv;

import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvHeader {
    private String id;
    private String title;
    private List<CsvHeader> children;
    private Integer repeat;
    private JpaFormField field;

    public void addChildren(CsvHeader child) {
        if (Objects.isNull(children)) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void setMaxRepeat(int repeat) {
        if (Objects.isNull(this.repeat)) {
            this.repeat = 1;
        }
        if (repeat > this.repeat) {
            this.repeat = repeat;
        }
    }

    public boolean hasChildren() {
        return !ObjectUtils.isEmpty(children);
    }

    public List<String> extract() {
        if (!hasChildren()) {
            return List.of(title);
        }

        List<String> results = new ArrayList<>();
        for (var i = 0; i < repeat; i++) {
            for (var nestedHeader : children) {
                var headerName = String.format("%s (%s #%d)", nestedHeader.title, title, i + 1);
                results.add(headerName);
            }
        }
        return results;
    }
}
