package com.formlaez.infrastructure.docengine.preprocessor;

import com.formlaez.infrastructure.docengine.variable.Variable;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.ArrayList;
import java.util.List;

public class PreProcessor {

    private final List<IPreProcessor> preProcessors;

    {
        preProcessors = new ArrayList<>();
        preProcessors.add(new LoopReplicator());
        preProcessors.add(new TableRowReplicator());
    }

    public void execute(XWPFDocument document, Variable vars) {
        for (IPreProcessor preProcessor : preProcessors) {
            preProcessor.execute(document, vars);
        }
    }
}
