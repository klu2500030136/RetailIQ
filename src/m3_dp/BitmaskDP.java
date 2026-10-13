package m3_dp;

import model.Product;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BitmaskDP {

    /**
     * Finds an optimal subset of products using Bitmask DP.
     * Objective: Maximize the number of distinct required categories covered.
     * Constraint: Total price <= budget.
     * 
     * O(2^n) where n is candidate size. Impractical for n > 20.
     */
    public static BundleOptimizationResult optimize(List<Product> candidates, double budget, Set<String> requiredCategories) {
        int n = candidates.size();
        if (n > 20) {
            throw new IllegalArgumentException("Bitmask DP is restricted to small candidate sets (n <= 20) because its state space grows exponentially.");
        }
        
        int numStates = 1 << n;
        
        int bestMask = 0;
        int maxScore = -1;
        double minCostForMaxScore = Double.MAX_VALUE;
        
        for (int mask = 0; mask < numStates; mask++) {
            double cost = 0;
            Set<String> covered = new HashSet<>();
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    cost += candidates.get(i).getPrice();
                    if (requiredCategories.contains(candidates.get(i).getCategory())) {
                        covered.add(candidates.get(i).getCategory());
                    }
                }
            }
            
            if (cost <= budget) {
                int score = covered.size();
                // Secondary objective: minimize cost if scores are equal
                // Or secondary objective: maximize number of items. Let's just maximize score, then minimize cost.
                if (score > maxScore || (score == maxScore && cost < minCostForMaxScore)) {
                    maxScore = score;
                    minCostForMaxScore = cost;
                    bestMask = mask;
                }
            }
        }
        
        List<Product> selected = new ArrayList<>();
        Set<String> finalCovered = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                selected.add(candidates.get(i));
                finalCovered.add(candidates.get(i).getCategory());
            }
        }
        
        return new BundleOptimizationResult(selected, minCostForMaxScore, finalCovered, budget - minCostForMaxScore, maxScore);
    }
}
