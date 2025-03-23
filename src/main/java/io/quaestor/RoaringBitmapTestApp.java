package io.quaestor;

import io.quaestor.idsets.roaring.RoaringBitmapIDSet;

public class RoaringBitmapTestApp {
    public static void main(String[] args) {
        RoaringBitmapIDSet set = new RoaringBitmapIDSet();
        for (int i = 0; i < 100000; i++) {
            set.add(i);
        }
        for (int i = 0; i < 100000; i++) {
            System.out.println(i + " " + set.contains(i));
        }
    }
}
