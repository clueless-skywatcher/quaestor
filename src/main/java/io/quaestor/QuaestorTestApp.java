package io.quaestor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import io.quaestor.document.TextDoc;
import io.quaestor.document.TextDocWithScore;
import io.quaestor.fields.TextualField;
import io.quaestor.index.AbstractIndexMaker;
import io.quaestor.index.InMemoryIndexMaker;
import io.quaestor.schema.IndexSchema;
import io.quaestor.search.SingleWordRankSearcher;
import io.quaestor.tokenizer.AbstractTokenizer;
import io.quaestor.tokenizer.BasicTokenizer;

public class QuaestorTestApp {
    public static void main(String[] args) throws Exception {
        IndexSchema schema = new IndexSchema("title", "content");

        AbstractTokenizer tokenizer = new BasicTokenizer();
        AbstractIndexMaker indexer = new InMemoryIndexMaker(tokenizer, schema);

        // Read sentences from a text file and index them
        try (BufferedReader reader = new BufferedReader(new FileReader("sentences.txt"))) {
            String line;
            int docId = 1;
            while ((line = reader.readLine()) != null) {
                TextDoc doc = new TextDoc(schema);
                doc.addField(new TextualField("title", "Document " + docId));
                doc.addField(new TextualField("content", line));
                indexer.addDocument(doc);
                docId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SingleWordRankSearcher searcher = new SingleWordRankSearcher(indexer);

        String termsToSearch = "java";
        long time = System.currentTimeMillis();
        List<TextDocWithScore> docs = searcher.search("content", termsToSearch);
        time = System.currentTimeMillis() - time;

        System.out.println("Time taken: " + time + " ms");

        for (TextDocWithScore doc : docs) {
            System.out.println(doc.toString());
        }
    }
}