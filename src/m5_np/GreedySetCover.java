package m5_np;

import model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GreedySetCover {
    public static SetCoverResult coverCategories(List<Product> products, Set<String> requiredCategories) {
        List<Product> selected = new ArrayList<>();
        Set<String> covered = new HashSet<>();
        Set<String> coveredKeys = new HashSet<>();
        Set<String> requiredKeys = new HashSet<>();
        for (String category : requiredCategories) requiredKeys.add(canonical(category));
        String[] categoryKeys = new String[products.size()];
        for (int i = 0; i < products.size(); i++) categoryKeys[i] = canonical(products.get(i).getCategory());
        boolean[] used = new boolean[products.size()];
        double totalCost = 0.0;

        while (!coveredKeys.containsAll(requiredKeys)) {
            int bestIndex = -1;
            double bestScore = -1.0;
            for (int i = 0; i < products.size(); i++) {
                if (used[i]) continue;
                Product product = products.get(i);
                String key = categoryKeys[i];
                int gain = requiredKeys.contains(key) && !coveredKeys.contains(key) ? 1 : 0;
                if (gain == 0) continue;
                double score = gain / Math.max(1.0, product.getPrice());
                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = i;
                }
            }
            if (bestIndex < 0) break;
            Product chosen = products.get(bestIndex);
            used[bestIndex] = true;
            selected.add(chosen);
            covered.add(chosen.getCategory());
            coveredKeys.add(categoryKeys[bestIndex]);
            totalCost += chosen.getPrice();
        }
        return new SetCoverResult(selected, covered, totalCost, coveredKeys.containsAll(requiredKeys));
    }

    private static String canonical(String category) {
        String key = category == null ? "" : category.trim().toLowerCase();
        if (key.endsWith("ies") && key.length() > 3) return key.substring(0, key.length() - 3) + "y";
        if (key.endsWith("s") && key.length() > 1) return key.substring(0, key.length() - 1);
        return key;
    }
}
