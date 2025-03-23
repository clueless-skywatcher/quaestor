package io.quaestor.schema;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class IndexSchema {
    private @Getter List<String> fields;

    public IndexSchema(String ...args) {
        this.fields = Arrays.asList(args);
    }

    public boolean contains(String field) {
        return fields.contains(field);
    }
}
