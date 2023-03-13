package com.formlaez.infrastructure.docengine.preprocessor;

import com.formlaez.infrastructure.docengine.expression.Expression;
import com.formlaez.infrastructure.docengine.expression.LoopExpressionExtractor;
import com.formlaez.infrastructure.docengine.util.DocumentUtils;
import com.formlaez.infrastructure.docengine.util.PreProcessorUtils;
import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class LoopReplicator implements IPreProcessor {

    @Override
    public void execute(XWPFDocument document, Variable vars) {
        LoopExpressionExtractor loopVariableExpression = new LoopExpressionExtractor();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        String loopKey = null;
        List<Replicates<XWPFParagraph>> loopParagraphs = new ArrayList<>();
        List<XWPFParagraph> items = null;
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph p = paragraphs.get(i);
            String content = p.getText();
            Expression expressionInfo = loopVariableExpression.extractFromText(content);
            if (expressionInfo != null) {
                loopKey = expressionInfo.getContent();
                items = new ArrayList<>();
            }
            if (loopKey != null) {
                items.add(p);
            }
            if (loopKey != null && loopVariableExpression.isEnd(content)) {
                Replicates<XWPFParagraph> replicateItems = Replicates.<XWPFParagraph>builder()
                        .items(items)
                        .key(loopKey)
                        .cursor(p.getCTP().newCursor())
                        .build();
                loopParagraphs.add(replicateItems);
                loopKey = null;
            }
        }

        for (Replicates<XWPFParagraph> replicateItems : loopParagraphs) {
            Variable var = vars.getPropertyValue(replicateItems.getKey());
            replicate(document, var.arrayLength() - 1, replicateItems);
        }

    }

    private void replicate(XWPFDocument document, int replicate, Replicates<XWPFParagraph> replicateItems) {
        List<XWPFParagraph> paragraphs = replicateItems.getItems();
        Collections.reverse(paragraphs);
        XmlCursor cursor = replicateItems.getCursor();
        int rIndex = replicate;
        while (rIndex > 0) {
            for (int i = 1; i < paragraphs.size() - 1; i++) {
                XWPFParagraph p = paragraphs.get(i);
                XWPFParagraph newP = document.insertNewParagraph(cursor);
                DocumentUtils.cloneParagraph(newP, p);
                PreProcessorUtils.updateExpression(replicateItems.getKey(), rIndex + 1, newP.getRuns());
                cursor = newP.getCTP().newCursor();
            }
            rIndex--;
        }
        for (int i = 1; i < paragraphs.size() - 1; i++) {
            XWPFParagraph p = paragraphs.get(i);
            PreProcessorUtils.updateExpression(replicateItems.getKey(), 1, p.getRuns());
        }
        document.removeBodyElement(document.getPosOfParagraph(paragraphs.get(0)));
        document.removeBodyElement(document.getPosOfParagraph(paragraphs.get(paragraphs.size() - 1)));
    }



}
