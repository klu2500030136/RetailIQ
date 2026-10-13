package tests;

import core.DataLoader;
import core.RetailDataStore;
import m5_np.*;

import java.util.HashSet;
import java.util.Set;

public class M5NPTests {
    public static void main(String[] args) {
        System.out.println("Running M5 NP/Approximation Tests...\n");
        boolean allPassed = true;
        allPassed &= testSetCover();
        allPassed &= testKnapsack();
        allPassed &= testSat();
        allPassed &= testVertexCover();
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static boolean testSetCover() {
        try {
            RetailDataStore ds = new DataLoader("data").loadAll();
            Set<String> required = new HashSet<>();
            required.add("Laptops");
            required.add("Smartphones");
            SetCoverResult result = GreedySetCover.coverCategories(ds.getProducts(), required);
            if (!result.complete) throw new RuntimeException("Expected all categories covered");
            if (!result.coveredCategories.contains("Laptops") || !result.coveredCategories.contains("Smartphones")) {
                throw new RuntimeException("Missing required category");
            }
            System.out.println("[PASS] GreedySetCover");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] GreedySetCover: " + e.getMessage());
            return false;
        }
    }

    private static boolean testKnapsack() {
        try {
            RetailDataStore ds = new DataLoader("data").loadAll();
            KnapsackResult result = ApproximateKnapsack.greedyByRatingSales(ds.getProducts(), 600.0);
            if (result.totalCost > 600.0) throw new RuntimeException("Budget exceeded");
            if (result.selectedProducts.isEmpty()) throw new RuntimeException("Expected selected products");
            System.out.println("[PASS] ApproximateKnapsack");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] ApproximateKnapsack: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSat() {
        try {
            CNFFormula formula = new CNFFormula(2);
            formula.addClause(1, 2);
            formula.addClause(-1, 2);
            formula.addClause(1, -2);
            SatResult result = SimpleSatSolver.solve(formula);
            if (!result.satisfiable) throw new RuntimeException("Expected satisfiable formula");

            CNFFormula impossible = new CNFFormula(1);
            impossible.addClause(1);
            impossible.addClause(-1);
            if (SimpleSatSolver.solve(impossible).satisfiable) throw new RuntimeException("Expected unsatisfiable formula");
            System.out.println("[PASS] SimpleSatSolver");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SimpleSatSolver: " + e.getMessage());
            return false;
        }
    }

    private static boolean testVertexCover() {
        try {
            int[][] edges = {{0, 1}, {1, 2}, {2, 3}};
            boolean[] cover = VertexCoverApproximation.twoApproximation(4, edges);
            for (int[] edge : edges) {
                if (!cover[edge[0]] && !cover[edge[1]]) throw new RuntimeException("Uncovered edge");
            }
            System.out.println("[PASS] VertexCoverApproximation");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] VertexCoverApproximation: " + e.getMessage());
            return false;
        }
    }
}
