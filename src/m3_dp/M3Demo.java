package m3_dp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.Product;

public class M3Demo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("M3 DP ALGORITHM BENCHMARK");
        System.out.println("========================================");
        
        benchmarkEditDistances();
        benchmarkAlignment();
        benchmarkBitmaskDP();
        benchmarkMatrixChain();
    }
    
    private static void benchmarkEditDistances() {
        String source = "wireless bluetooth headphones with long battery life".repeat(10);
        String target = "premium wireless bluetooth headphones with battery life".repeat(10);
        
        System.out.println("\n--- Edit Distances ---");
        System.out.println("String lengths: " + source.length() + " vs " + target.length());
        
        long t1 = System.nanoTime();
        LevenshteinDistance.distance(source, target);
        long t2 = System.nanoTime();
        System.out.println("Levenshtein          : " + (t2 - t1) / 1000000.0 + " ms");
        
        t1 = System.nanoTime();
        DamerauLevenshtein.distance(source, target);
        t2 = System.nanoTime();
        System.out.println("Damerau-Levenshtein  : " + (t2 - t1) / 1000000.0 + " ms");
        
        WeightedEditDistance wed = new WeightedEditDistance(1, 1, 2);
        t1 = System.nanoTime();
        wed.distance(source, target);
        t2 = System.nanoTime();
        System.out.println("Weighted Edit        : " + (t2 - t1) / 1000000.0 + " ms");
    }
    
    private static void benchmarkAlignment() {
        String seqA = "ACACACTA".repeat(50);
        String seqB = "AGCACACA".repeat(50);
        
        System.out.println("\n--- Alignment ---");
        System.out.println("Sequence lengths: " + seqA.length() + " vs " + seqB.length());
        
        NeedlemanWunsch nw = new NeedlemanWunsch(2, -1, -2);
        long t1 = System.nanoTime();
        nw.align(seqA, seqB);
        long t2 = System.nanoTime();
        System.out.println("Needleman-Wunsch     : " + (t2 - t1) / 1000000.0 + " ms");
        
        SmithWaterman sw = new SmithWaterman(2, -1, -2);
        t1 = System.nanoTime();
        sw.align(seqA, seqB);
        t2 = System.nanoTime();
        System.out.println("Smith-Waterman       : " + (t2 - t1) / 1000000.0 + " ms");
    }
    
    private static void benchmarkBitmaskDP() {
        System.out.println("\n--- Bitmask DP ---");
        for (int n : new int[]{5, 10, 15, 20}) {
            List<Product> candidates = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Product p = new Product("P" + i, "Prod" + i, "Cat" + (i % 3), "Desc", 100.0, 5.0, 10, "W1", 0);
                candidates.add(p);
            }
            long t1 = System.nanoTime();
            BitmaskDP.optimize(candidates, 10000, new HashSet<>());
            long t2 = System.nanoTime();
            System.out.println("Bitmask DP (N=" + n + ")    : " + (t2 - t1) / 1000000.0 + " ms");
        }
    }
    
    private static void benchmarkMatrixChain() {
        System.out.println("\n--- Matrix Chain Multiplication ---");
        int[] p = {10, 30, 5, 60, 12, 50, 20, 15, 40, 10, 50, 30, 25, 40, 10, 5};
        System.out.println("Number of matrices: " + (p.length - 1));
        
        long t1 = System.nanoTime();
        MatrixChainMultiplication.optimize(p);
        long t2 = System.nanoTime();
        System.out.println("Matrix Chain DP      : " + (t2 - t1) / 1000000.0 + " ms");
    }
}
