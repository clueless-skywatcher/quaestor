package io.quaestor.search;

import java.util.List;

import io.quaestor.document.TextDocWithScore;

public interface AbstractRankSearcher {
    public abstract List<TextDocWithScore> search(String field, String query);
}
