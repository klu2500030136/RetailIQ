package m6_random_parallel;

import java.util.Random;

public class RandomizedQuickSort {
    private static final int INSERTION_SORT_CUTOFF = 16;

    public static void sort(int[] values, long seed) {
        quickSort(values, 0, values.length - 1, new Random(seed));
    }

    private static void quickSort(int[] a, int lo, int hi, Random random) {
        if (lo >= hi) return;
        if (hi - lo <= INSERTION_SORT_CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }
        int pivotIndex = lo + random.nextInt(hi - lo + 1);
        swap(a, pivotIndex, hi);
        int p = partition(a, lo, hi);
        quickSort(a, lo, p - 1, random);
        quickSort(a, p + 1, hi, random);
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

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int value = a[i];
            int j = i - 1;
            while (j >= lo && a[j] > value) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = value;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
