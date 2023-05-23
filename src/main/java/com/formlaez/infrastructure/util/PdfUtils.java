package com.formlaez.infrastructure.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class PdfUtils {

    public static String extractRawText(MultipartFile file) {
        try {
            InputStream pdfInputStream = file.getInputStream();
            return extractRawText(pdfInputStream);
        } catch (IOException e) {
            return null;
        }
    }

    public static String extractRawText(InputStream pdfInputStream) {
        try (PDDocument document = PDDocument.load(pdfInputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            return null;
        }
    }
}
