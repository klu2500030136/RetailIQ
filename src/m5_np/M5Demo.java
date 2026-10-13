package m5_np;

import core.DataLoader;
import core.RetailDataStore;

import java.util.HashSet;
import java.util.Set;

public class M5Demo {
    public static void main(String[] args) {
        RetailDataStore ds = new DataLoader("data").loadAll();
        Set<String> required = new HashSet<>();
        required.add("Laptops");
        required.add("Smartphones");
        required.add("Headphones");
        SetCoverResult cover = GreedySetCover.coverCategories(ds.getProducts(), required);
        System.out.println("M5 greedy set cover complete: " + cover.complete + ", products: " + cover.selectedProducts.size());

        KnapsackResult knapsack = ApproximateKnapsack.greedyByRatingSales(ds.getProducts(), 1500.0);
        System.out.println("M5 approximate product portfolio cost: " + String.format("%.2f", knapsack.totalCost)
                + ", value: " + String.format("%.2f", knapsack.totalValue));

        CNFFormula formula = new CNFFormula(3);
        formula.addClause(1, -2);
        formula.addClause(2, 3);
        formula.addClause(-1, -3);
        SatResult sat = SimpleSatSolver.solve(formula);
        System.out.println("M5 small SAT satisfiable: " + sat.satisfiable);

        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        boolean[] coverVertices = VertexCoverApproximation.twoApproximation(5, edges);
        System.out.println("M5 vertex-cover approximation: " + VertexCoverApproximation.selectedVertices(coverVertices));
    }
}
