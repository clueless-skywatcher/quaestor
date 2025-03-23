package io.quaestor.fields;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class TextualField implements AbstractField {
    private @Getter String content;
    private @Getter String name;
    private @Getter @Setter UUID docId;

    public TextualField(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String toString() {
        return String.format("TextualField(%s)", content);
    }
}
