package com.formlaez.infrastructure.docengine;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocumentLoader {

    public XWPFDocument load(String path) throws IOException {
        try(InputStream inputStream = new FileInputStream(path)) {
            return new XWPFDocument(inputStream);
        }
    }

    public XWPFDocument load(InputStream inputStream) throws IOException {
        return new XWPFDocument(inputStream);
    }
}
