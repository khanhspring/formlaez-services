package com.formlaez.infrastructure.util;

import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MsWordUtils {

    public static String extractRawText(MultipartFile file) {
        try {
            InputStream msWordInputStream = file.getInputStream();
            return extractRawText(msWordInputStream);
        } catch (IOException e) {
            return null;
        }
    }

    public static String extractRawText(InputStream msWordInputStream) {
        try (XWPFDocument doc = new XWPFDocument(msWordInputStream);) {
            POITextExtractor extractor = new XWPFWordExtractor(doc);
            return extractor.getText();
        } catch (IOException e) {
            return null;
        }
    }
}
