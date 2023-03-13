package com.formlaez.infrastructure.docengine.preprocessor;

import com.formlaez.infrastructure.docengine.common.TableAttributeKeys;
import com.formlaez.infrastructure.docengine.expression.Expression;
import com.formlaez.infrastructure.docengine.expression.TableExpressionExtractor;
import com.formlaez.infrastructure.docengine.expression.attribute.ExpressionAttribute;
import com.formlaez.infrastructure.docengine.util.DocumentUtils;
import com.formlaez.infrastructure.docengine.util.PreProcessorUtils;
import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

@AllArgsConstructor
public class TableRowReplicator implements IPreProcessor {

    @Override
    public void execute(XWPFDocument document, Variable vars) {
        List<XWPFTable> tables = document.getTables();
        TableExpressionExtractor tableExpressionExtractor = new TableExpressionExtractor();
        for (XWPFTable table : tables) {
            int pos = document.getPosOfTable(table);
            if (pos == 0) {
                continue;
            }
            IBodyElement element = document.getBodyElements().get(pos - 1);
            if (element instanceof XWPFParagraph) {
                XWPFParagraph p = (XWPFParagraph) element;
                String pText = p.getText();
                Expression expression = tableExpressionExtractor.extractFromText(pText);
                if (expression != null) {
                    replicate(table, vars, expression);
                    document.removeBodyElement(document.getPosOfParagraph(p));
                }
            }
        }
    }

    public void replicate(XWPFTable table, Variable vars, Expression expression) {
        ExpressionAttribute attribute = expression.getAttribute();
        Integer replicateRow = null;
        if (attribute != null) {
            replicateRow = attribute.getAttrAsInteger(TableAttributeKeys.ROW.getKeyName());
        }
        if (replicateRow == null) {
            replicateRow = table.getNumberOfRows();
        }
        replicateRow--;
        XWPFTableRow row = table.getRow(replicateRow);
        Variable var = vars.getPropertyValue(expression.getContent());

        int replicate = var.arrayLength() - 1; // 1 row already existed

        int rIndex = replicate;
        while (rIndex > 0) {
            XWPFTableRow cloneR = DocumentUtils.cloneRow(row);
            table.addRow(cloneR, replicateRow);
            rIndex--;
        }

        int index = 1;
        for (int i = replicateRow; i <= replicateRow + replicate; i++) {
            XWPFTableRow r = table.getRow(i);
            for (XWPFTableCell cell : r.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    PreProcessorUtils.updateExpression(expression.getContent(), index, paragraph.getRuns());
                }
            }
            index++;
        }
        DocumentUtils.commitTableRows(table);
    }

}
