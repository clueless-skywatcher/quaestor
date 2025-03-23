package io.quaestor.index;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

import io.quaestor.document.TextDoc;
import io.quaestor.fields.AbstractField;
import io.quaestor.schema.IndexSchema;
import io.quaestor.tokenizer.AbstractTokenizer;

public class PlainInvertedIndex implements AbstractIndex {
    private Map<String, Map<String, Set<Integer>>> index;
    private Map<Integer, TextDoc> documents;
    private AbstractTokenizer tokenizer;
    private IndexSchema schema;

    private AtomicInteger idToUse = new AtomicInteger(1);

    public PlainInvertedIndex(AbstractTokenizer tokenizer, IndexSchema schema) {
        this();
        this.schema = schema;
        this.tokenizer = tokenizer;
    }

    public PlainInvertedIndex() {
        this.index = new HashMap<>();
        this.documents = new HashMap<>();
    }

    public void addDocument(TextDoc doc) throws Exception {
        if (!doc.getFieldNames().equals(new HashSet<>(schema.getFields()))) {
            throw new Exception("Doc does not conform to schema");
        }

        doc.setId(idToUse.getAndIncrement());
        documents.put(doc.getId(), doc);
        for (String fieldName: doc.getFieldNames()) {
            AbstractField field = doc.getField(fieldName);
            String[] contentTokens = tokenizer.tokenize(field.getContent());
            for (String token: contentTokens) {
                if (!index.containsKey(token)) {
                    index.put(token, new HashMap<>());
                }
                if (!index.get(token).containsKey(fieldName)) {
                    index.get(token).put(fieldName, new HashSet<>());
                }

                index.get(token).get(fieldName).add(doc.getId());
            }
        }
    }

    public Set<TextDoc> getInstances(String[] tokens, String field) {
        Set<TextDoc> resultSet = new HashSet<>();
        for (String token: tokens) {
            Set<Integer> set = index.get(token).get(field);
            if (set != null) {
                for (int i: set) {
                    resultSet.add(documents.get(i));
                }
            }
        }

        return resultSet;
    }

    public int getDocCount() {
        return documents.size();
    }

    public int getDocFrequency(String term, String field) {
        term = tokenizer.tokenize(term)[0];
        
        if (index.containsKey(term)) {
            Set<TextDoc> docs = new HashSet<>();
            for (int docId: index.get(term).get(field)) {
                TextDoc document = documents.get(docId);
                docs.add(document);
            }

            return docs.size();
        }

        return 0;
    }

    public TextDoc getDoc(int id) {
        return documents.get(id);
    }
}
