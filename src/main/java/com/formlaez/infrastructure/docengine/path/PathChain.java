package com.formlaez.infrastructure.docengine.path;

import com.formlaez.infrastructure.docengine.variable.Variable;
import lombok.Getter;
import lombok.Setter;

public abstract class PathChain {
    private PathChain next;
    private PathChain prev;
    @Getter
    @Setter
    private PathChain root;

    public PathChain append(PathChain next) {
        this.next = next;
        this.next.prev = this;
        this.next.root = root;
        return next;
    }

    public PathChain prev() {
        if (isRoot()) {
            throw new IllegalStateException("Current path token is a root");
        }
        return prev;
    }

    public PathChain next() {
        if (isLeaf()) {
            throw new IllegalStateException("Current path token is a leaf");
        }
        return next;
    }

    public boolean isLeaf() {
        return next == null;
    }

    public boolean isRoot() {
        return prev == null;
    }

    public abstract Variable evaluate(Variable var);
}
