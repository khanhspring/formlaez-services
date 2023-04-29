package com.formlaez.infrastructure.docengine.processor;

import com.formlaez.infrastructure.docengine.docextractor.DocumentRunExtractor;
import com.formlaez.infrastructure.docengine.expression.Expression;
import com.formlaez.infrastructure.docengine.expression.ExpressionExtractor;
import com.formlaez.infrastructure.docengine.path.PathExecutor;
import com.formlaez.infrastructure.docengine.util.DocumentUtils;
import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Log4j2
public class Processor {

    private static final String BLANK_IMAGE = "R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==";

    public void execute(XWPFDocument document, Variable vars) {
        processParagraphs(document.getParagraphs(), vars);
        processTables(document.getTables(), vars);
    }

    private void processParagraphs(List<XWPFParagraph> paragraphs, Variable vars) {
        for (XWPFParagraph p : paragraphs) {
            processRuns(p.getRuns(), vars);
            processTextBoxes(p, vars);
            processPictures(p, vars);
        }
    }

    private void processTables(List<XWPFTable> tables, Variable vars) {
        for (XWPFTable table : tables) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        if (!cell.getTables().isEmpty()) {
                            processTables(cell.getTables(), vars);
                        }
                        processRuns(paragraph.getRuns(), vars);
                        processTextBoxes(paragraph, vars);
                        processPictures(paragraph, vars);
                    }
                }
            }
            DocumentUtils.commitTableRows(table);
        }
    }

    private void processTextBoxes(XWPFParagraph paragraph, Variable vars) {
        DocumentRunExtractor runExtractor = new DocumentRunExtractor(paragraph);
        List<XWPFRun> runs = runExtractor.getRuns();
        processRuns(runs, vars);
        for (XWPFRun run : runs) {
            var obj = runExtractor.getXmlObject(run);
            obj.set(run.getCTR());
        }
    }

    private void processPictures(XWPFParagraph paragraph, Variable vars) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (var run: runs) {
            var pics = run.getEmbeddedPictures();
            if (pics == null || pics.isEmpty()) {
                return;
            }
            for (XWPFPicture pic : pics) {
                processPicture(vars, run, pic);
            }
        }
    }

    private void processPicture(Variable vars, XWPFRun run, XWPFPicture pic) {
        ExpressionExtractor extractor = new ExpressionExtractor();
        PathExecutor pathExecutor = new PathExecutor();
        Expression expression = extractor.extractFromText(pic.getDescription());
        if (expression == null) {
            return;
        }

        String value = pathExecutor.execute(expression.getContent().trim(), vars);
        if (ObjectUtils.isEmpty(value)) {
            value = BLANK_IMAGE;
        }
        value = value.replace("data:image/png;base64,", "");
        byte[] imgBytes = Base64.getDecoder().decode(value);
        try(InputStream inputStream = new ByteArrayInputStream(imgBytes);) {
            XWPFDocument document = run.getParent().getDocument();
            String relationId = document.addPictureData(inputStream, Document.PICTURE_TYPE_PNG);
            pic.getCTPicture().getBlipFill().getBlip().setEmbed(relationId);
            pic.getCTPicture().getNvPicPr().getCNvPr().unsetDescr(); // not working
        } catch (InvalidFormatException | IOException e) {
            log.warn("Process images error", e);
        }
    }

    private void processRuns(List<XWPFRun> runs, Variable vars) {
        if (runs.isEmpty()) {
            return;
        }
        ExpressionExtractor extractor = new ExpressionExtractor();
        PathExecutor pathExecutor = new PathExecutor();
        for (XWPFRun run : runs) {
            String runText = run.getText(0);
            if (runText == null) {
                continue;
            }
            Expression expression = extractor.extractFromText(runText);
            if (expression != null) {
                String value = pathExecutor.execute(expression.getContent().trim(), vars);
                if (value != null) {
                    int startExpression = runText.indexOf(expression.getFullExpression());
                    runText = runText.substring(0, startExpression)
                            + value
                            + runText.substring(startExpression + expression.getFullExpression().length());
                    run.setText(runText, 0);
                }
            }
        }
    }
}
