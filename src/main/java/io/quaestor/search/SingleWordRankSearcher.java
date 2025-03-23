package io.quaestor.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.quaestor.document.TextDoc;
import io.quaestor.document.TextDocWithScore;
import io.quaestor.index.AbstractIndexMaker;
import io.quaestor.index.AbstractIndex;
import io.quaestor.ranking.RankingAlgorithm;
import io.quaestor.ranking.TFIDFRanking;
import io.quaestor.tokenizer.AbstractTokenizer;
import io.quaestor.tokenizer.BasicTokenizer;

public class SingleWordRankSearcher implements AbstractRankSearcher {
    private AbstractIndex index;
    private RankingAlgorithm algorithm;
    private AbstractTokenizer tokenizer;

    public SingleWordRankSearcher(AbstractIndex index) {
        this.index = index;
        this.tokenizer = new BasicTokenizer();
    }

    public SingleWordRankSearcher(AbstractIndex index, AbstractTokenizer tokenizer) {
        this.index = index;
        this.algorithm = new TFIDFRanking(tokenizer);
        this.tokenizer = tokenizer;
    }

    public SingleWordRankSearcher(AbstractIndex index, RankingAlgorithm algorithm) {
        this.index = index;
        this.algorithm = algorithm;
        this.tokenizer = new BasicTokenizer();
    }

    public SingleWordRankSearcher(AbstractIndex index, RankingAlgorithm algorithm, AbstractTokenizer tokenizer) {
        this.index = index;
        this.algorithm = algorithm;
        this.tokenizer = tokenizer;
    }

    public SingleWordRankSearcher(AbstractIndexMaker indexMaker) {
        this(indexMaker.getIndex(), new BasicTokenizer());
    }

    public SingleWordRankSearcher(AbstractIndexMaker indexer, AbstractTokenizer tokenizer) {
        this(indexer.getIndex(), tokenizer);
    }

    public SingleWordRankSearcher(AbstractIndexMaker indexer, RankingAlgorithm algorithm, AbstractTokenizer tokenizer) {
        this(indexer.getIndex(), algorithm, tokenizer);
    }

    public List<TextDocWithScore> search(String field, String queryString) {
        String[] tokens = tokenizer.tokenize(queryString);

        Set<TextDoc> entry = index.getInstances(tokens, field);
        if (entry == null) {
            return new ArrayList<>();
        }
        String[] query = tokenizer.tokenize(queryString);

        Map<Integer, Double> scores = algorithm.rank(Arrays.asList(query), field, entry, index);

        List<TextDocWithScore> resultList = new ArrayList<>();

        scores.entrySet().stream()
            .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
            .forEach(e -> {
                TextDoc doc = index.getDoc(e.getKey());
                resultList.add(new TextDocWithScore(e.getValue(), doc));
            });

        return resultList;
    }
}
