package com.formlaez.infrastructure.docengine.preprocessor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.xmlbeans.XmlCursor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Replicates<T> {
    private String key;
    private List<T> items;
    private XmlCursor cursor;
}
