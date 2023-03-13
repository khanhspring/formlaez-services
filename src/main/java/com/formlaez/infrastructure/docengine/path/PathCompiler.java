package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.common.SequenceIterator;
import com.formlaez.infrastructure.docengine.common.SymbolSignals;
import com.formlaez.infrastructure.docengine.exception.InvalidPathException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PathCompiler {

    private final SequenceIterator iterator;

    public PathCompiler(String fullPath) {
        this.iterator = new SequenceIterator(fullPath);
    }

    public PathChain compile() {
        PathAppender appender = new PathAppender();
        readNextToken(appender);
        return appender.getRoot();
    }

    private void readNextToken(PathAppender appender) {
        if (!iterator.hasNext()) {
            return;
        }
        iterator.next();
        char c = iterator.currentChar();
        switch (c) {
            case SymbolSignals.OPEN_SQUARE_BRACKET:
                readArrayIndexToken(appender);
                break;
            case SymbolSignals.PERIOD:
                readDotToken(appender);
                break;
            default:
                readPropertyToken(appender);
                break;
        }
    }

    private void readDotToken(PathAppender appender) {
        if (!iterator.hasNext()) {
            throw new InvalidPathException("PathChain must not end with a '.'");
        }
        readNextToken(appender);
    }

    private void readPropertyToken(PathAppender appender) {
        int startPosition = iterator.getPosition();

        while (iterator.hasNext()) {
            char c = iterator.nextChar();
            if (SymbolSignals.PERIOD == c || SymbolSignals.OPEN_SQUARE_BRACKET == c) {
                break;
            }
            iterator.next();
        }
        String pathContent = iterator.subSequence(startPosition, iterator.getPosition() + 1);
        PathChain pathToken = new PropertyPathChain(pathContent);
        appender.append(pathToken);
        readNextToken(appender);
    }

    private void readArrayIndexToken(PathAppender appender) {
        iterator.next();
        int startPosition = iterator.getPosition();

        while (iterator.hasNext()) {
            char c = iterator.nextChar();
            if (SymbolSignals.CLOSE_SQUARE_BRACKET == c) {
                break;
            }
            iterator.next();
        }
        String pathContent = iterator.subSequence(startPosition, iterator.getPosition() + 1);

        if (!pathContent.matches("[0-9]+")) {
            throw new InvalidPathException("Array index must be a number");
        }
        PathChain pathToken = new ArrayIndexPathChain(Integer.valueOf(pathContent));
        appender.append(pathToken);

        iterator.next(); // skip ]
        readNextToken(appender);
    }

    public void reset() {
        this.iterator.setPosition(0);
    }
}
