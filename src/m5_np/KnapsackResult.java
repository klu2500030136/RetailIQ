package m5_np;

import model.Product;

import java.util.List;

public class KnapsackResult {
    public final List<Product> selectedProducts;
    public final double totalCost;
    public final double totalValue;

    public KnapsackResult(List<Product> selectedProducts, double totalCost, double totalValue) {
        this.selectedProducts = selectedProducts;
        this.totalCost = totalCost;
        this.totalValue = totalValue;
    }
}
