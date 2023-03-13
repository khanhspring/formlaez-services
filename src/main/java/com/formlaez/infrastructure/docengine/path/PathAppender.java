package com.formlaez.infrastructure.docengine.path;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathAppender {
    private PathChain root;
    private PathChain current;

    public void append(PathChain pathToken) {
        if (root == null) {
            pathToken.setRoot(pathToken);
            root = pathToken;
        } else {
            current.append(pathToken);
        }
        current = pathToken;
    }
}
