package io.quaestor.ranking;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.quaestor.document.TextDoc;
import io.quaestor.index.AbstractIndex;

public interface RankingAlgorithm {
    public Map<Integer, Double> rank(List<String> query, String field, Set<TextDoc> docs, AbstractIndex index);
}
