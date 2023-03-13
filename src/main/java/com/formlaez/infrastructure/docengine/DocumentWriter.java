package com.formlaez.infrastructure.docengine;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DocumentWriter {

    public void write(XWPFDocument document, String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        document.write(outputStream);
    }

    public void write(XWPFDocument document, OutputStream outputStream) throws IOException {
        document.write(outputStream);
    }
}
