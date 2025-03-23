package io.quaestor.index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import io.quaestor.document.TextDoc;
import io.quaestor.fields.AbstractField;
import io.quaestor.idsets.roaring.RoaringBitmapIDSet;
import io.quaestor.tokenizer.AbstractTokenizer;

public class RoaringBitmapInvertedIndex implements AbstractIndex {
    private Map<String, Map<String, RoaringBitmapIDSet>> index;
    private Map<Integer, TextDoc> documents;
    private AbstractTokenizer tokenizer;

    private AtomicInteger idToUse = new AtomicInteger(1);

    public RoaringBitmapInvertedIndex(AbstractTokenizer tokenizer) {
        this();
        this.tokenizer = tokenizer;
    }

    public RoaringBitmapInvertedIndex() {
        this.index = new HashMap<>();
        this.documents = new HashMap<>();
    }


    @Override
    public void addDocument(TextDoc doc) {
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
                    index.get(token).put(fieldName, new RoaringBitmapIDSet());
                }

                index.get(token).get(fieldName).add(doc.getId());
            }
        }
    }

    @Override
    public Set<TextDoc> getInstances(String[] tokens, String field) {
        Set<TextDoc> resultSet = new HashSet<>();
        for (String token: tokens) {
            if (index.get(token) != null) {
                RoaringBitmapIDSet set = index.get(token).get(field);
                if (set != null) {
                    for (int i: set.getElements()) {
                        resultSet.add(documents.get(i));
                    }
                }
            }            
        }

        return resultSet;
    }

    @Override
    public int getDocCount() {
        return documents.size();
    }

    @Override
    public int getDocFrequency(String term, String field) {
        term = tokenizer.tokenize(term)[0];
        
        if (index.containsKey(term)) {
            Set<TextDoc> docs = new HashSet<>();
            for (int docId: index.get(term).get(field).getElements()) {
                TextDoc document = documents.get(docId);
                docs.add(document);
            }

            return docs.size();
        }

        return 0;
    }

    @Override
    public TextDoc getDoc(int id) {
        return documents.get(id);
    }
    
}
