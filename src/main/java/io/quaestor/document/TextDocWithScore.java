package io.quaestor.document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TextDocWithScore {
    private @Getter Double score;
    private @Getter TextDoc doc;

    public String toString() {
        return String.format("Document: %s, Score: %f", doc.toString(), score);
    }
}
