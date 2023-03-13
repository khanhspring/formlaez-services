package com.formlaez.infrastructure.docengine.expression;

import com.formlaez.infrastructure.docengine.expression.attribute.ExpressionAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expression {
    private String fullExpression;
    private String content;
    private ExpressionType type;
    private ExpressionAttribute attribute;
}
