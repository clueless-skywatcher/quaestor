package io.quaestor.idsets.roaring;

import java.util.ArrayList;
import java.util.List;

public class RoaringBitmapIDSet {
    private List<AbstractIDContainer> containers = new ArrayList<>();

    public final int ARRAY_TO_BITMAP_THRESHOLD = (int) Math.pow(2, 12);

    private final int BUCKET_COUNT = (int) Math.pow(2, 16);

    public void add(int n) {
        int bucket = n >> 16;
        int floor = n & 0xffff;

        if (n > 32767) {
            System.out.println();
        }

        if (containers.size() < bucket + 1) {
            while (containers.size() != bucket + 1) {
                containers.add(new SortedIDContainer());
            }
        }

        if (containers.get(bucket) instanceof SortedIDContainer && containers.get(bucket).getCount() >= ARRAY_TO_BITMAP_THRESHOLD) {
            BitmapIDContainer newContainer = new BitmapIDContainer();
            for (Integer sh: containers.get(bucket).getElements()) {
                newContainer.add(sh);
            }     
            containers.set(bucket, newContainer);       
        }
        containers.get(bucket).add(floor);
    }

    public boolean contains(int n) {
        int bucket = n >> 16;
        int floor = n % BUCKET_COUNT;

        if (containers.size() < bucket) {
            return false;
        }

        if (containers.get(bucket) == null) {
            return false;
        } else {
            return containers.get(bucket).contains(floor);
        }
    }

    public List<Integer> getElements() {
        List<Integer> intSet = new ArrayList<>();

        for (int i = 0; i < containers.size(); i++) {
            int highBits = i;
            AbstractIDContainer container = containers.get(i);
            for (int lowBits: container.getElements()) {
                int val = (highBits << 16) | (lowBits & 0xFFFF);
                intSet.add(val);
            }
        }

        return intSet;
    }
}
