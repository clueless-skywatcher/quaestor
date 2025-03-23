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
import io.quaestor.search.RankSearcher;
import io.quaestor.tokenizer.AbstractTokenizer;
import io.quaestor.tokenizer.BasicTokenizer;

public class QuaestorTestApp {
    public static void main(String[] args) {
        AbstractTokenizer tokenizer = new BasicTokenizer();
        AbstractIndexMaker indexer = new InMemoryIndexMaker(tokenizer);

        // Read sentences from a text file and index them
        try (BufferedReader reader = new BufferedReader(new FileReader("sentences.txt"))) {
            String line;
            int docId = 1;
            while ((line = reader.readLine()) != null) {
                TextDoc doc = new TextDoc();
                doc.addField(new TextualField("title", "Document " + docId));
                doc.addField(new TextualField("content", line));
                indexer.addDocument(doc);
                docId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RankSearcher searcher = new RankSearcher(indexer);

        String termsToSearch = "java";
        List<TextDocWithScore> docs = searcher.search("content", termsToSearch);

        for (TextDocWithScore doc : docs) {
            System.out.println(doc.toString());
        }
    }
}