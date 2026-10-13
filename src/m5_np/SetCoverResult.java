package m5_np;

import model.Product;

import java.util.List;
import java.util.Set;

public class SetCoverResult {
    public final List<Product> selectedProducts;
    public final Set<String> coveredCategories;
    public final double totalCost;
    public final boolean complete;

    public SetCoverResult(List<Product> selectedProducts, Set<String> coveredCategories, double totalCost, boolean complete) {
        this.selectedProducts = selectedProducts;
        this.coveredCategories = coveredCategories;
        this.totalCost = totalCost;
        this.complete = complete;
    }
}
