package com.formlaez.infrastructure.docengine.preprocessor;

import com.formlaez.infrastructure.docengine.variable.Variable;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface IPreProcessor {
    void execute(XWPFDocument document, Variable vars);
}
