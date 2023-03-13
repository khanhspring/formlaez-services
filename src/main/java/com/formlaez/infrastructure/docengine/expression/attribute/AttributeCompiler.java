package com.formlaez.infrastructure.docengine.expression.attribute;

import com.formlaez.infrastructure.docengine.common.SymbolSignals;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AttributeCompiler {
    private final String fullPath;

    public ExpressionAttribute compile() {
        ExpressionAttribute attribute = new ExpressionAttribute(fullPath);
        String[] propertyArr = fullPath.split(String.valueOf(SymbolSignals.COMMA));
        for (String p : propertyArr) {
            String[] pKV = p.split(String.valueOf(SymbolSignals.COLON));
            if (pKV.length == 2) {
                attribute.addAttr(pKV[0], pKV[1]);
            } else if (pKV.length == 1) {
                attribute.addAttr(pKV[0], "");
            }
        }
        return attribute;
    }
}
