package m5_np;

import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ApproximateKnapsack {
    private static class ScoredProduct {
        final Product product;
        final double value;
        final double density;

        ScoredProduct(Product product) {
            this.product = product;
            this.value = value(product);
            this.density = value / Math.max(1.0, product.getPrice());
        }
    }

    public static KnapsackResult greedyByRatingSales(List<Product> products, double budget) {
        ScoredProduct[] sorted = new ScoredProduct[products.size()];
        for (int i = 0; i < products.size(); i++) sorted[i] = new ScoredProduct(products.get(i));
        quickSortByDensity(sorted, 0, sorted.length - 1);
        List<Product> selected = new ArrayList<>();
        double cost = 0.0;
        double value = 0.0;
        for (ScoredProduct scored : sorted) {
            Product product = scored.product;
            if (cost + product.getPrice() <= budget) {
                selected.add(product);
                cost += product.getPrice();
                value += scored.value;
            }
        }
        return new KnapsackResult(selected, cost, value);
    }

    private static double value(Product product) {
        return product.getRating() * 100.0 + product.getSalesCount();
    }

    private static void quickSortByDensity(ScoredProduct[] a, int lo, int hi) {
        if (lo >= hi) return;
        double pivot = a[(lo + hi) / 2].density;
        int i = lo;
        int j = hi;
        while (i <= j) {
            while (a[i].density > pivot) i++;
            while (a[j].density < pivot) j--;
            if (i <= j) {
                ScoredProduct tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i++;
                j--;
            }
        }
        quickSortByDensity(a, lo, j);
        quickSortByDensity(a, i, hi);
    }
}
