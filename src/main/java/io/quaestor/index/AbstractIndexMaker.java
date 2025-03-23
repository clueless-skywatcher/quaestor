package io.quaestor.index;

import io.quaestor.document.TextDoc;

public interface AbstractIndexMaker {
    public void addDocument(TextDoc doc) throws Exception;
    public AbstractIndex getIndex();
    public void commit();
}
