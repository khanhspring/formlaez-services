package com.formlaez.infrastructure.docengine.util;

import com.formlaez.infrastructure.docengine.expression.Expression;
import com.formlaez.infrastructure.docengine.expression.ExpressionBuilder;
import com.formlaez.infrastructure.docengine.expression.ExpressionType;
import com.formlaez.infrastructure.docengine.expression.SimpleExpressionExtractor;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

public class PreProcessorUtils {
    public static void updateExpression(String loopKey, int index, List<XWPFRun> runs) {
        SimpleExpressionExtractor simpleVariableExpression = new SimpleExpressionExtractor();
        ExpressionBuilder expressionBuilder = new ExpressionBuilder();

        String loopKeyIndex = loopKey + "[" + index + "]";
        for (XWPFRun run : runs) {
            String runText = run.getText(0);
            if (runText == null) {
                continue;
            }
            Expression simpleExpression = simpleVariableExpression.extractFromText(runText);
            if (simpleExpression != null) {
                String newContent = "";
                if ("index".equalsIgnoreCase(simpleExpression.getContent())) {
                    newContent = index+"";
                } else {
                    newContent = expressionBuilder.build(ExpressionType.SIMPLE,
                            loopKeyIndex, simpleExpression.getContent());
                }
                int startExpression = runText.indexOf(simpleExpression.getFullExpression());
                runText = runText.substring(0, startExpression)
                        + newContent
                        + runText.substring(startExpression + simpleExpression.getFullExpression().length());
                run.setText(runText, 0);
            }
        }
    }
}
