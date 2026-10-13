package m6_random_parallel;

import java.util.Random;

public class RandomizedQuickSelect {
    public static int selectKthSmallest(int[] values, int k, long seed) {
        if (k < 0 || k >= values.length) throw new IllegalArgumentException("k out of range");
        int[] copy = new int[values.length];
        for (int i = 0; i < values.length; i++) copy[i] = values[i];
        return select(copy, 0, copy.length - 1, k, new Random(seed));
    }

    private static int select(int[] a, int lo, int hi, int k, Random random) {
        while (lo <= hi) {
            int pivotIndex = lo + random.nextInt(hi - lo + 1);
            swap(a, pivotIndex, hi);
            int p = partition(a, lo, hi);
            if (p == k) return a[p];
            if (k < p) hi = p - 1;
            else lo = p + 1;
        }
        return a[k];
    }

    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
