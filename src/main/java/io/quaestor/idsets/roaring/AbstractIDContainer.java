package io.quaestor.idsets.roaring;

import java.util.List;

public interface AbstractIDContainer {
    boolean contains(int value);
    void add(int value);
    int getCount();
    AbstractIDContainer union(AbstractIDContainer other);
    AbstractIDContainer intersection(AbstractIDContainer other);

    List<Integer> getElements();
}
