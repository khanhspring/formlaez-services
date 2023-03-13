package com.formlaez.infrastructure.docengine.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionExecutor {
    private static final Pattern SEGMENT_PATTERN = Pattern.compile("^(.*)(\\[(\\d+)\\])$");

    public void execute(Expression expression, Map<String, Object> vars) {
        List<ExpressionSegment> segments = split(expression);
        Object val = execute(segments, vars);
    }

    private List<ExpressionSegment> split(Expression expression) {
        String expContent = expression.getContent();
        String[] strSegments = expContent.split("\\.");
        List<ExpressionSegment> segments = new ArrayList<>();
        for (String s : strSegments) {
            Matcher matcher = SEGMENT_PATTERN.matcher(s);
            if (matcher.matches()) {
                String key = matcher.group(1);
                String indexStr = matcher.group(3);
                Integer index = null;
                if (indexStr != null) {
                    index = Integer.valueOf(indexStr);
                }
                ExpressionSegment segment = ExpressionSegment.builder()
                        .key(key)
                        .index(index)
                        .build();
                segments.add(segment);
            }
        }
        return segments;
    }

    private Object execute(List<ExpressionSegment> segments, Map<String, Object> vars) {
        Object current = vars;
        for (ExpressionSegment segment : segments) {
            if (current instanceof Map) {
                current = ((Map) current).get(segment.getKey());
                if (segment.getIndex() != null && current instanceof List) {
                    current = ((List) current).get(segment.getIndex());
                }
            }
        }
        return current;
    }
}
