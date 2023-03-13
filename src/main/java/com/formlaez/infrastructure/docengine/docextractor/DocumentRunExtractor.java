package com.formlaez.infrastructure.docengine.docextractor;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Getter
public class DocumentRunExtractor {

    private List<XWPFRun> runs;
    private List<XmlObject> objects;
    private Map<XWPFRun, XmlObject> runMap;

    public static final String XML_PATH = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r";

    public DocumentRunExtractor(XWPFParagraph paragraph) {
        runs = new ArrayList<>();
        objects = new ArrayList<>();
        runMap = new HashMap<>();
        extract(paragraph);
    }

    public void extract(XWPFParagraph paragraph) {
        try {
            XmlCursor cursor = paragraph.getCTP().newCursor();
            cursor.selectPath(XML_PATH);

            while(cursor.hasNextSelection()) {
                cursor.toNextSelection();
                XmlObject obj = cursor.getObject();
                objects.add(obj);

                CTR ctr = CTR.Factory.parse(obj.xmlText());
                XWPFRun run = new XWPFRun(ctr, (IRunBody)paragraph);
                runs.add(run);
                runMap.put(run, obj);
            }
        } catch (XmlException e) {
            log.warn("Can not extract run", e);
        }
    }

    public XmlObject getXmlObject(XWPFRun run) {
        return runMap.get(run);
    }
}
