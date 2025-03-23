package io.quaestor.search;

import java.util.List;

import io.quaestor.document.TextDoc;

public interface AbstractSearcher {
    public List<TextDoc> search(String field, String query);
}
