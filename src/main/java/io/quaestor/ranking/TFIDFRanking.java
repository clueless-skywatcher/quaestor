package io.quaestor.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.quaestor.document.TextDoc;
import io.quaestor.fields.AbstractField;
import io.quaestor.index.AbstractIndex;
import io.quaestor.tokenizer.AbstractTokenizer;

public class TFIDFRanking implements RankingAlgorithm {
    private AbstractTokenizer tokenizer;

    public TFIDFRanking(AbstractTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Map<Integer, Double> rank(List<String> query, String field, Set<TextDoc> docs, AbstractIndex index) {
        Map<Integer, Double> resultMap = new HashMap<>();

        Map<String, Double> idfs = new HashMap<>();

        for (String term : query) {
            idfs.put(term, idf(term, index, field));
        }

        for (TextDoc doc: docs) {
            double score = 0.0;
            for (String term: query) {
                score += tf(term, field, doc) * idfs.get(term);
            }

            resultMap.put(doc.getId(), score);
        }

        return resultMap;
    }

    private double tf(String term, String fieldName, TextDoc doc) {
        int terms = 0;
        int totalTerms = 0;

        AbstractField field = doc.getField(fieldName);
        String[] words = tokenizer.tokenize(field.getContent());

        for (String word: words) {
            if (word.equalsIgnoreCase(term)) {
                terms++;
            }
            totalTerms++;
        }
        return (double) terms / totalTerms;
    }

    private double idf(String term, AbstractIndex index, String field) {
        int totalDocs = index.getDocCount();
        int frequency = index.getDocFrequency(term, field);

        if (frequency == 0) {
            return 0.0;
        }

        return Math.log10((double) totalDocs / frequency);
    }

}
