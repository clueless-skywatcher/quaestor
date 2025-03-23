package io.quaestor.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.quaestor.document.TextDoc;
import io.quaestor.index.AbstractIndex;
import io.quaestor.index.AbstractIndexMaker;

public class ExactWordSearcher {
    private AbstractIndex index;

    public ExactWordSearcher(AbstractIndex index) {
        this.index = index;
    }

    public ExactWordSearcher(AbstractIndexMaker indexer) {
        this(indexer.getIndex());
    }

    public List<TextDoc> search(String field, String searchTerm) {
        String[] tokens = new String[]{searchTerm};
        Set<TextDoc> entry = index.getInstances(tokens, field);
        if (entry == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entry);
    }
}
