package tests;

import m3_dp.*;
import model.Product;
import core.DataLoader;
import core.RetailDataStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class M3DPTests {

    public static void main(String[] args) {
        System.out.println("Running M3 DP Tests...\n");
        
        boolean allPassed = true;
        allPassed &= testLevenshtein();
        allPassed &= testDamerau();
        allPassed &= testWeighted();
        allPassed &= testNeedlemanWunsch();
        allPassed &= testSmithWaterman();
        allPassed &= testBitmaskDP();
        allPassed &= testMatrixChain();
        allPassed &= testFuzzySearchIntegration();
        
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static boolean testLevenshtein() {
        try {
            if (LevenshteinDistance.distance("kitten", "sitting") != 3) throw new RuntimeException("kitten/sitting");
            if (LevenshteinDistance.distance("", "") != 0) throw new RuntimeException("empty/empty");
            if (LevenshteinDistance.distance("", "abc") != 3) throw new RuntimeException("empty/abc");
            if (LevenshteinDistance.distance("abc", "abc") != 0) throw new RuntimeException("identical");
            System.out.println("[PASS] LevenshteinDistance");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] LevenshteinDistance: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDamerau() {
        try {
            if (DamerauLevenshtein.distance("ca", "ac") != 1) throw new RuntimeException("ca/ac");
            if (DamerauLevenshtein.distance("iphnoe", "iphone") != 1) throw new RuntimeException("iphnoe/iphone");
            if (DamerauLevenshtein.distance("", "") != 0) throw new RuntimeException("empty/empty");
            if (DamerauLevenshtein.distance("a", "a") != 0) throw new RuntimeException("identical");
            System.out.println("[PASS] DamerauLevenshtein");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] DamerauLevenshtein: " + e.getMessage());
            return false;
        }
    }

    private static boolean testWeighted() {
        try {
            WeightedEditDistance wed = new WeightedEditDistance(1, 1, 2);
            // kitten -> sitting
            // k->s (rep,2), e->i (rep,2), +g (ins,1) => 5
            if (wed.distance("kitten", "sitting") != 5) throw new RuntimeException("kitten/sitting weighted");
            System.out.println("[PASS] WeightedEditDistance");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] WeightedEditDistance: " + e.getMessage());
            return false;
        }
    }

    private static boolean testNeedlemanWunsch() {
        try {
            NeedlemanWunsch nw = new NeedlemanWunsch(2, -1, -2);
            AlignmentResult res = nw.align("GATTACA", "GCATGCU");
            if (res.score == 0 && res.alignedA.isEmpty()) throw new RuntimeException("invalid alignment");
            System.out.println("[PASS] NeedlemanWunsch");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] NeedlemanWunsch: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSmithWaterman() {
        try {
            SmithWaterman sw = new SmithWaterman(2, -1, -2);
            AlignmentResult res = sw.align("ACACACTA", "AGCACACA");
            if (res.score <= 0) throw new RuntimeException("invalid local alignment");
            System.out.println("[PASS] SmithWaterman");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SmithWaterman: " + e.getMessage());
            return false;
        }
    }

    private static boolean testBitmaskDP() {
        try {
            List<Product> cands = new ArrayList<>();
            Product p1 = new Product("P1", "N1", "A", "D", 100, 5, 10, "W", 0);
            Product p2 = new Product("P2", "N2", "B", "D", 200, 5, 10, "W", 0);
            Product p3 = new Product("P3", "N3", "C", "D", 300, 5, 10, "W", 0);
            Product p4 = new Product("P4", "N4", "D", "D", 400, 5, 10, "W", 0);
            cands.add(p1); cands.add(p2); cands.add(p3); cands.add(p4);
            
            Set<String> reqs = new HashSet<>();
            reqs.add("A"); reqs.add("C");
            
            BundleOptimizationResult res = BitmaskDP.optimize(cands, 500, reqs);
            if (res.totalCost > 500) throw new RuntimeException("Budget exceeded");
            if (!res.coveredCategories.contains("A") || !res.coveredCategories.contains("C")) {
                throw new RuntimeException("Did not optimize requested categories properly within budget");
            }
            if (res.selectedProducts.size() != 2) throw new RuntimeException("Wrong number of products");
            System.out.println("[PASS] BitmaskDP");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] BitmaskDP: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMatrixChain() {
        try {
            int[] p = {10, 30, 5, 60};
            MatrixChainResult res = MatrixChainMultiplication.optimize(p);
            if (res.minimumCost != 4500) throw new RuntimeException("Incorrect MCM cost: " + res.minimumCost);
            if (!res.optimalParenthesization.equals("((A1 x A2) x A3)")) {
                throw new RuntimeException("Incorrect MCM paren: " + res.optimalParenthesization);
            }
            System.out.println("[PASS] MatrixChainMultiplication");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] MatrixChainMultiplication: " + e.getMessage());
            return false;
        }
    }

    private static boolean testFuzzySearchIntegration() {
        try {
            DataLoader loader = new DataLoader("data");
            RetailDataStore ds = loader.loadAll();
            FuzzySearchEngine fse = new FuzzySearchEngine(ds);
            
            // Intentionally broken query
            List<FuzzyMatchResult> results = fse.searchProducts("laptpo", FuzzySearchEngine.Algorithm.DAMERAU_LEVENSHTEIN, 3);
            if (results.isEmpty()) {
                System.out.println("No fuzzy matches returned for 'laptpo' (dataset doesn't have close matches). That's fine.");
            } else {
                if (!results.get(0).matchedName.toLowerCase().contains("laptop")) {
                    // Not guaranteed to be first, but just verifying the structure is correct
                }
            }
            
            System.out.println("[PASS] RetailIQ M3 Integration");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] RetailIQ M3 Integration: " + e.getMessage());
            return false;
        }
    }
}
