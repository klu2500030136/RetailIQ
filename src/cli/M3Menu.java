package cli;

import core.RetailDataStore;
import m3_dp.*;
import model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class M3Menu {
    private final InputHelper input;
    private final RetailDataStore dataStore;
    private final FuzzySearchEngine fuzzyEngine;
    private final ProductAlignmentEngine alignEngine;

    public M3Menu(InputHelper input, RetailDataStore dataStore) {
        this.input = input;
        this.dataStore = dataStore;
        this.fuzzyEngine = new FuzzySearchEngine(dataStore);
        this.alignEngine = new ProductAlignmentEngine(dataStore);
    }

    public void show() {
        while (true) {
            System.out.println("\n========== M3 ADVANCED DP ANALYTICS ==========");
            System.out.println("1. Fuzzy Product Search");
            System.out.println("2. Compare Product Names");
            System.out.println("3. Product Description Alignment");
            System.out.println("4. Review / Text Local Alignment");
            System.out.println("5. Product Bundle Optimization");
            System.out.println("6. Matrix Chain Optimization");
            System.out.println("7. DP Algorithm Lab");
            System.out.println("8. M3 Benchmark");
            System.out.println("0. Back");
            
            int choice = input.readInt("Enter choice: ", 0, 8);
            if (choice == 0) return;
            
            switch (choice) {
                case 1: fuzzySearch(); break;
                case 2: compareNames(); break;
                case 3: descriptionAlignment(); break;
                case 4: localTextAlignment(); break;
                case 5: bundleOptimization(); break;
                case 6: matrixChainOptimization(); break;
                case 7: dpLab(); break;
                case 8: M3Demo.main(new String[0]); input.pause(); break;
            }
        }
    }

    private void fuzzySearch() {
        String query = input.readString("Enter product query: ");
        System.out.println("1. Levenshtein\n2. Damerau-Levenshtein\n3. Weighted Edit Distance\n4. Compare All");
        int algoChoice = input.readInt("Choice: ", 1, 4);
        
        System.out.println("\n========== FUZZY SEARCH ==========");
        System.out.println("Query: " + query);
        
        if (algoChoice == 1 || algoChoice == 4) {
            List<FuzzyMatchResult> lev = fuzzyEngine.searchProducts(query, FuzzySearchEngine.Algorithm.LEVENSHTEIN, 3);
            printFuzzyResults("Levenshtein", lev);
        }
        if (algoChoice == 2 || algoChoice == 4) {
            List<FuzzyMatchResult> dam = fuzzyEngine.searchProducts(query, FuzzySearchEngine.Algorithm.DAMERAU_LEVENSHTEIN, 3);
            printFuzzyResults("Damerau-Levenshtein", dam);
        }
        if (algoChoice == 3 || algoChoice == 4) {
            List<FuzzyMatchResult> wed = fuzzyEngine.searchProducts(query, FuzzySearchEngine.Algorithm.WEIGHTED, 4);
            printFuzzyResults("Weighted Edit", wed);
        }
        input.pause();
    }
    
    private void printFuzzyResults(String algo, List<FuzzyMatchResult> results) {
        System.out.println("\n--- " + algo + " ---");
        if (results.isEmpty()) {
            System.out.println("No close candidates found.");
            return;
        }
        for (int i = 0; i < Math.min(3, results.size()); i++) {
            System.out.println((i + 1) + ". " + results.get(i).matchedName);
            System.out.println("   Distance: " + results.get(i).distance);
        }
    }

    private void compareNames() {
        String idA = input.readString("Product A ID: ");
        String idB = input.readString("Product B ID: ");
        
        Product pA = getProduct(idA);
        Product pB = getProduct(idB);
        
        if (pA == null || pB == null) {
            System.out.println("Invalid Product IDs.");
            return;
        }
        
        String nA = pA.getName();
        String nB = pB.getName();
        
        System.out.println("\nComparing:\nA: " + nA + "\nB: " + nB);
        System.out.println("Levenshtein         : " + LevenshteinDistance.distance(nA, nB));
        System.out.println("Damerau-Levenshtein : " + DamerauLevenshtein.distance(nA, nB));
        System.out.println("Weighted (1,1,2)    : " + new WeightedEditDistance(1, 1, 2).distance(nA, nB));
        input.pause();
    }

    private void descriptionAlignment() {
        String idA = input.readString("Product A ID: ");
        String idB = input.readString("Product B ID: ");
        System.out.println("1. Needleman-Wunsch\n2. Smith-Waterman\n3. Compare Both");
        int c = input.readInt("Choice: ", 1, 3);
        
        if (c == 1 || c == 3) {
            System.out.println("\n--- Needleman-Wunsch (Global) ---");
            AlignmentResult r = alignEngine.alignProductDescriptions(idA, idB, ProductAlignmentEngine.AlignmentType.GLOBAL_NEEDLEMAN_WUNSCH);
            printAlignment(r);
        }
        if (c == 2 || c == 3) {
            System.out.println("\n--- Smith-Waterman (Local) ---");
            AlignmentResult r = alignEngine.alignProductDescriptions(idA, idB, ProductAlignmentEngine.AlignmentType.LOCAL_SMITH_WATERMAN);
            printAlignment(r);
        }
        input.pause();
    }

    private void localTextAlignment() {
        String txtA = input.readString("Enter Custom Text A: ");
        String txtB = input.readString("Enter Custom Text B: ");
        AlignmentResult r = alignEngine.alignText(txtA, txtB, ProductAlignmentEngine.AlignmentType.LOCAL_SMITH_WATERMAN);
        System.out.println("\n--- Smith-Waterman (Local) ---");
        printAlignment(r);
        input.pause();
    }

    private void bundleOptimization() {
        int n = input.readInt("How many candidate products? (max 20): ", 1, 20);
        List<Product> candidates = new ArrayList<>();
        
        // Randomly pick N from the store for demo if they don't want to type them manually, 
        // or just pick the first N. Let's just pick the first N to keep it simple, or prompt.
        System.out.println("Using first " + n + " products as candidates for demonstration.");
        for (int i = 0; i < n && i < dataStore.getProducts().size(); i++) {
            Product p = dataStore.getProducts().get(i);
            candidates.add(p);
            System.out.println((i+1) + ". " + p.getProductId() + " (" + p.getCategory() + ") - $" + p.getPrice());
        }
        
        double budget = input.readInt("Budget: ", 0, 100000);
        String reqs = input.readString("Required categories (comma separated): ");
        Set<String> reqSet = new HashSet<>();
        for (String r : reqs.split(",")) if (!r.trim().isEmpty()) reqSet.add(r.trim());
        
        BundleOptimizationResult res = BitmaskDP.optimize(candidates, budget, reqSet);
        
        System.out.println("\n========== OPTIMAL PRODUCT BUNDLE ==========");
        System.out.println("Selected Products:");
        for (Product p : res.selectedProducts) {
            System.out.println("- " + p.getProductId() + " : " + p.getName() + " ($" + p.getPrice() + ")");
        }
        System.out.println("Total Cost: $" + res.totalCost);
        System.out.println("Remaining Budget: $" + res.remainingBudget);
        System.out.println("Covered Categories: " + res.coveredCategories);
        System.out.println("Objective (Num Categories): " + res.objectiveValue);
        input.pause();
    }

    private void matrixChainOptimization() {
        int n = input.readInt("Number of matrices: ", 1, 15);
        int[] dims = new int[n + 1];
        System.out.println("Enter dimensions (p0 to p" + n + "):");
        for (int i = 0; i <= n; i++) {
            dims[i] = input.readInt("p" + i + ": ", 1, 10000);
        }
        
        MatrixChainResult r = MatrixChainMultiplication.optimize(dims);
        System.out.println("\nMinimum Scalar Multiplications: " + r.minimumCost);
        System.out.println("Optimal Parenthesization: " + r.optimalParenthesization);
        input.pause();
    }

    private void dpLab() {
        while (true) {
            System.out.println("\n========== DP ALGORITHM LAB ==========");
            System.out.println("1. Levenshtein");
            System.out.println("2. Damerau-Levenshtein");
            System.out.println("3. Weighted Edit Distance");
            System.out.println("4. Needleman-Wunsch");
            System.out.println("5. Smith-Waterman");
            System.out.println("0. Back");
            int c = input.readInt("Choice: ", 0, 5);
            if (c == 0) return;
            
            String a = input.readString("String A: ");
            String b = input.readString("String B: ");
            
            switch (c) {
                case 1: System.out.println("Dist: " + LevenshteinDistance.distance(a, b)); break;
                case 2: System.out.println("Dist: " + DamerauLevenshtein.distance(a, b)); break;
                case 3: System.out.println("Dist (1,1,2): " + new WeightedEditDistance(1,1,2).distance(a, b)); break;
                case 4: printAlignment(new NeedlemanWunsch(2,-1,-2).align(a,b)); break;
                case 5: printAlignment(new SmithWaterman(2,-1,-2).align(a,b)); break;
            }
            input.pause();
        }
    }
    
    private void printAlignment(AlignmentResult r) {
        if (r == null) {
            System.out.println("No alignment could be calculated.");
            return;
        }
        System.out.println("Alignment Score: " + r.score);
        System.out.println("Aligned A: " + r.alignedA);
        System.out.println("Aligned B: " + r.alignedB);
    }
    
    private Product getProduct(String id) {
        for(Product p : dataStore.getProducts()) {
            if (p.getProductId().equals(id)) return p;
        }
        return null;
    }
}
