package io.quaestor.index;

import io.quaestor.document.TextDoc;
import io.quaestor.schema.IndexSchema;
import io.quaestor.tokenizer.AbstractTokenizer;

import lombok.Getter;

public class InMemoryIndexMaker implements AbstractIndexMaker {
    private @Getter AbstractTokenizer tokenizer;
    private @Getter AbstractIndex index;

    public InMemoryIndexMaker(AbstractTokenizer tokenizer, IndexSchema schema) {
        this.tokenizer = tokenizer;
        this.index = new RoaringBitmapInvertedIndex(tokenizer, schema);
    }

    public void addDocument(TextDoc doc) throws Exception {
        index.addDocument(doc);
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("In-memory indexes don't need to be committed");
    }
}
