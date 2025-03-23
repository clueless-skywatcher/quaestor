package io.quaestor.index;

import io.quaestor.document.TextDoc;
import io.quaestor.tokenizer.AbstractTokenizer;

import lombok.Getter;

public class InMemoryIndexMaker implements AbstractIndexMaker {
    private @Getter AbstractTokenizer tokenizer;
    private @Getter AbstractIndex index;

    public InMemoryIndexMaker(AbstractTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.index = new RoaringBitmapInvertedIndex(tokenizer);
    }

    public void addDocument(TextDoc doc) {
        index.addDocument(doc);
    }
}
