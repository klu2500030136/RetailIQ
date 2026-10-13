package m3_dp;

import core.RetailDataStore;
import m1_string.StringSearchEngine;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FuzzySearchEngine {
    public enum Algorithm {
        LEVENSHTEIN,
        DAMERAU_LEVENSHTEIN,
        WEIGHTED
    }

    private final RetailDataStore dataStore;
    private final StringSearchEngine m1Engine;

    public FuzzySearchEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
        this.m1Engine = new StringSearchEngine(dataStore);
    }

    public List<FuzzyMatchResult> searchProducts(String query, Algorithm algo, int maxDistance) {
        // Step 1: Exact search using M1
        List<Product> exactMatches = m1Engine.searchProductsKMP(query);
        List<FuzzyMatchResult> results = new ArrayList<>();
        
        for (Product p : exactMatches) {
            results.add(new FuzzyMatchResult(p, 0, "KMP (Exact)", p.getName()));
        }

        // If we found exact matches, we can still include close fuzzy matches or just return
        // We'll calculate fuzzy distances for everything not exactly matched or just all of them
        
        WeightedEditDistance wed = new WeightedEditDistance(1, 1, 2); // default weights
        
        for (Product p : dataStore.getProducts()) {
            if (exactMatches.contains(p)) continue;
            
            String target = p.getName().toLowerCase();
            String src = query.toLowerCase();
            
            int dist;
            switch (algo) {
                case DAMERAU_LEVENSHTEIN:
                    dist = DamerauLevenshtein.distance(src, target);
                    break;
                case WEIGHTED:
                    dist = wed.distance(src, target);
                    break;
                case LEVENSHTEIN:
                default:
                    dist = LevenshteinDistance.distance(src, target);
                    break;
            }
            
            if (dist <= maxDistance) {
                results.add(new FuzzyMatchResult(p, dist, algo.name(), p.getName()));
            }
        }
        
        Collections.sort(results);
        return results;
    }
}
