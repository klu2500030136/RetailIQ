package tests;

import m6_random_parallel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class M6RandomParallelTests {
    public static void main(String[] args) {
        System.out.println("Running M6 Randomized/Parallel Tests...\n");
        boolean allPassed = true;
        allPassed &= testQuickSort();
        allPassed &= testQuickSelect();
        allPassed &= testReservoir();
        allPassed &= testReducer();
        allPassed &= testBlellochScan();
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static boolean testQuickSort() {
        try {
            int[] values = {5, 1, 4, 2, 3};
            RandomizedQuickSort.sort(values, 11L);
            if (!Arrays.equals(values, new int[]{1, 2, 3, 4, 5})) throw new RuntimeException("Array not sorted");
            System.out.println("[PASS] RandomizedQuickSort");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] RandomizedQuickSort: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuickSelect() {
        try {
            int kth = RandomizedQuickSelect.selectKthSmallest(new int[]{9, 1, 7, 3, 5}, 2, 12L);
            if (kth != 5) throw new RuntimeException("Expected 5, got " + kth);
            System.out.println("[PASS] RandomizedQuickSelect");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] RandomizedQuickSelect: " + e.getMessage());
            return false;
        }
    }

    private static boolean testReservoir() {
        try {
            List<Integer> stream = new ArrayList<>();
            for (int i = 0; i < 100; i++) stream.add(i);
            List<Integer> sample = new ReservoirSampler<Integer>().sample(stream, 10, 5L);
            if (sample.size() != 10) throw new RuntimeException("Wrong sample size");
            System.out.println("[PASS] ReservoirSampler");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] ReservoirSampler: " + e.getMessage());
            return false;
        }
    }

    private static boolean testReducer() {
        try {
            int[] values = {1, 2, 3, 4, 5};
            if (ParallelReducer.sum(values, 3) != 15) throw new RuntimeException("Wrong sum");
            if (ParallelReducer.max(values, 3) != 5) throw new RuntimeException("Wrong max");
            System.out.println("[PASS] ParallelReducer");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] ParallelReducer: " + e.getMessage());
            return false;
        }
    }

    private static boolean testBlellochScan() {
        try {
            int[] result = BlellochScan.inclusivePrefixSum(new int[]{3, 1, 4, 1, 5});
            if (!Arrays.equals(result, new int[]{3, 4, 8, 9, 14})) {
                throw new RuntimeException("Wrong scan: " + Arrays.toString(result));
            }
            System.out.println("[PASS] BlellochScan");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] BlellochScan: " + e.getMessage());
            return false;
        }
    }
}
