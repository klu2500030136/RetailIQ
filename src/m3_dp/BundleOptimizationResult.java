package m3_dp;

import model.Product;
import java.util.List;
import java.util.Set;

public class BundleOptimizationResult {
    public final List<Product> selectedProducts;
    public final double totalCost;
    public final Set<String> coveredCategories;
    public final double remainingBudget;
    public final int objectiveValue;

    public BundleOptimizationResult(List<Product> selectedProducts, double totalCost, 
                                    Set<String> coveredCategories, double remainingBudget, 
                                    int objectiveValue) {
        this.selectedProducts = selectedProducts;
        this.totalCost = totalCost;
        this.coveredCategories = coveredCategories;
        this.remainingBudget = remainingBudget;
        this.objectiveValue = objectiveValue;
    }
}
