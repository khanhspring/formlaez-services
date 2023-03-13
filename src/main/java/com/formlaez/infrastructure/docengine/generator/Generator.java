package com.formlaez.infrastructure.docengine.generator;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface Generator {
    void generate(XWPFDocument document, String output);
}
