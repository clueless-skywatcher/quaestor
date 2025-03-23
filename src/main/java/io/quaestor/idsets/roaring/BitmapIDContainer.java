package io.quaestor.idsets.roaring;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitmapIDContainer implements AbstractIDContainer {
    private BitSet bitSet = new BitSet(16);

    @Override
    public boolean contains(int key) {
        return bitSet.get(key);
    }

    @Override
    public void add(int value) {
        bitSet.set(value);
    }

    @Override
    public int getCount() {
        return bitSet.size();
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
        List<Integer> elems = new ArrayList<>();
        int index = bitSet.nextSetBit(0);
        while (index >= 0) {
            elems.add(index);
            index = bitSet.nextSetBit(index + 1);
        }
        return elems;
    }
    
}
