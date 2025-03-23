package io.quaestor.fields;

import java.util.UUID;

public interface AbstractField {
    public String getName();
    public String getContent();
    
    public UUID getDocId();
    public void setDocId(UUID id);
}
