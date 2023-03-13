package com.formlaez.infrastructure.docengine.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpressionSegment {
    private String key;
    private Integer index; // can be null
}
