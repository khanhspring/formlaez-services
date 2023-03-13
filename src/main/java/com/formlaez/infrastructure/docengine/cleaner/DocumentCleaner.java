package com.formlaez.infrastructure.docengine.cleaner;

import com.formlaez.infrastructure.docengine.docextractor.DocumentRunExtractor;
import com.formlaez.infrastructure.docengine.expression.ExpressionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

@Log4j2
public class DocumentCleaner {

    public void clean(XWPFDocument document) {
        cleanParagraphs(document.getParagraphs());
        cleanTables(document.getTables());
    }

    public void cleanTables(List<XWPFTable> tables) {
        for (XWPFTable table : tables) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        if (!cell.getTables().isEmpty()) {
                            cleanTables(cell.getTables());
                        }
                        cleanRuns(paragraph.getRuns());
                        cleanTextBoxes(paragraph);
                    }
                }
            }
        }
    }

    public void cleanParagraphs(List<XWPFParagraph> paragraphs) {
        for (XWPFParagraph p : paragraphs) {
            cleanRuns(p.getRuns());
            cleanTextBoxes(p);
        }
    }

    public void cleanTextBoxes(XWPFParagraph paragraph) {
        DocumentRunExtractor runExtractor = new DocumentRunExtractor(paragraph);
        List<XWPFRun> runs = runExtractor.getRuns();
        cleanRuns(runs);
        for (XWPFRun run : runs) {
            var obj = runExtractor.getXmlObject(run);
            obj.set(run.getCTR());
        }
    }

    public void cleanRuns(List<XWPFRun> runs) {
        if (runs.isEmpty()) {
            return;
        }

        int expressionOpenPos = -1;
        int expressionOpenRunIndex = -1;
        int expressionClosePos = -1;
        int expressionCloseRunIndex = -1;
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.getText(0);
            if (runText == null) {
                runText = "";
            }
            int runTextLength = runText.length();
            int selectorIndex = ExpressionUtils.selectorIndex(runText);
            if (selectorIndex != -1) {
                expressionOpenRunIndex = i;
                if (selectorIndex + 1 < runTextLength) {
                    char afterSelectorChar = runText.charAt(selectorIndex + 1);
                    if (ExpressionUtils.isOpen(afterSelectorChar)) {
                        expressionOpenPos = selectorIndex;
                    }
                } else if (i + 1 < runs.size()) {
                    XWPFRun nextRun = runs.get(i+1);
                    String nextRunText = nextRun.getText(0);
                    if (nextRunText == null) {
                        nextRunText = "";
                    }
                    char nextRunFirstChar = nextRunText.charAt(0);
                    if (ExpressionUtils.isOpen(nextRunFirstChar)) {
                        expressionOpenPos = selectorIndex;
                    }
                }
            }
            if (expressionOpenPos != -1) {
                expressionClosePos = ExpressionUtils.closeIndex(runText);
                if (expressionClosePos != -1) {
                    expressionCloseRunIndex = i;
                }
            }
            if (expressionOpenPos != -1
                    && expressionClosePos != -1) {
                refactorRuns(runs, expressionOpenRunIndex, expressionCloseRunIndex);
                expressionOpenPos = -1;
                expressionClosePos = -1;
                expressionOpenRunIndex = -1;
                expressionCloseRunIndex = -1;
            }
        }
    }

    private void refactorRuns(List<XWPFRun> runs, int start, int end) {
        StringBuilder keyPartialContent = new StringBuilder();
        for (int i = start + 1; i < end; i++) {
            XWPFRun run = runs.get(i);
            keyPartialContent.append(run.getText(0));
            run.setText("", 0);
        }
        String runStartText = runs.get(start).getText(0);
        runs.get(start).setText(runStartText + keyPartialContent.toString(), 0);
        if (start == end) {
            return;
        }
        String runEndText = runs.get(end).getText(0);
        int closeAt = ExpressionUtils.closeIndex(runEndText);
        String keyEndContent = runEndText.substring(0, closeAt+1);

        runStartText = runs.get(start).getText(0);
        runs.get(start).setText(runStartText + keyEndContent, 0);

        String tailContent = runEndText.substring(closeAt+1);
        runs.get(end).setText(tailContent, 0);
    }

}
