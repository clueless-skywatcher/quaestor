package io.quaestor.idsets.roaring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedIDContainer implements AbstractIDContainer {
    private List<Integer> list = new ArrayList<>();
    
    @Override
    public boolean contains(int key) {
        return Collections.binarySearch(list, key) >= 0;
    }

    @Override
    public void add(int value) {
        if (list.size() == 0) {
            list.add(value);
        }

        int index = Collections.binarySearch(list, value);
        if (index < 0) {
            list.add(-index - 1, value);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AbstractIDContainer union(AbstractIDContainer other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'union'");
    }

    @Override
    public AbstractIDContainer intersection(AbstractIDContainer other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'intersection'");
    }

    @Override
    public List<Integer> getElements() {
        return list;
    }
    
}
