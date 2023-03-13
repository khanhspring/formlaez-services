package com.formlaez.infrastructure.docengine;

import com.formlaez.infrastructure.docengine.cleaner.DocumentCleaner;
import com.formlaez.infrastructure.docengine.preprocessor.PreProcessor;
import com.formlaez.infrastructure.docengine.processor.Processor;
import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Log4j2
public class DocumentProcessor {

    public void process(InputStream inputStream, OutputStream outputStream, Variable vars) {
        try {
            DocumentLoader loader = new DocumentLoader();
            DocumentCleaner cleaner = new DocumentCleaner();
            PreProcessor preProcessor = new PreProcessor();
            XWPFDocument document = loader.load(inputStream);
            cleaner.clean(document);
            preProcessor.execute(document, vars);
            Processor processor = new Processor();
            processor.execute(document, vars);
            DocumentWriter writer = new DocumentWriter();
            writer.write(document, outputStream);
            inputStream.close();
        } catch (Exception e) {
            log.warn("Process document error", e);
        }
    }

    public byte[] process(InputStream inputStream, Variable vars) {
        try {
            var outputStream = new ByteArrayOutputStream();
            process(inputStream, outputStream, vars);
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.warn("Process document error", e);
        }
        return new byte[0];
    }
}
