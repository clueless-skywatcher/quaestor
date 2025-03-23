package io.quaestor.queries;

import java.util.List;

import lombok.Getter;

public class FieldQuery implements AbstractQuery {
    private @Getter String fieldName;
    private @Getter List<String> terms;

    public FieldQuery(String fieldName, List<String> terms) {
        this.fieldName = fieldName;
        this.terms = terms;
    }
}
