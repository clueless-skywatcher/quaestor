package io.quaestor.index;

import java.util.Set;

import io.quaestor.document.TextDoc;

public interface AbstractIndex {
    public void addDocument(TextDoc doc) throws Exception;
    public Set<TextDoc> getInstances(String[] tokens, String field);
    public int getDocCount();
    public int getDocFrequency(String term, String field);
    public TextDoc getDoc(int id);
}
