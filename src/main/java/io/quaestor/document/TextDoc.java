package io.quaestor.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.quaestor.fields.AbstractField;
import io.quaestor.schema.IndexSchema;
import lombok.Getter;
import lombok.Setter;

public class TextDoc {
    private Map<String, AbstractField> fields;
    private @Getter @Setter int id;
    private IndexSchema schema;

    public TextDoc(IndexSchema schema) {
        this.schema = schema;
        this.fields = new HashMap<>();
    }

    public String getContent(String key) {
        return fields.get(key).getContent();
    }

    public void addField(AbstractField field) throws Exception {
        if (!schema.contains(field.getName())) {
            throw new Exception("Invalid fieldName: " + field.getName());
        }
        this.fields.put(field.getName(), field);
    }

    public Set<String> getFieldNames() {
        return this.fields.keySet();
    }

    public AbstractField getField(String key) {
        return this.fields.get(key);
    }

    public int hashCode() {
        return this.id;
    }

    public String toString() {
        return String.format("TextDoc(%d, %s)", getId(), fields.toString());
    }
}
