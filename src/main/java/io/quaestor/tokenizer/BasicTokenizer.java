package io.quaestor.tokenizer;

public class BasicTokenizer implements AbstractTokenizer {
    @Override
    public String[] tokenize(String str) {
        str = str.replaceAll("[^a-zA-Z ]", "");
        String[] tokens = str.toLowerCase().split("\\s+");
        return tokens;
    }    
}
