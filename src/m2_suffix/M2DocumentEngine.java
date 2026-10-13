package m2_suffix;

import core.RetailDataStore;
import model.Product;
import model.Review;

import java.util.ArrayList;
import java.util.List;

public class M2DocumentEngine {

    private final RetailDataStore dataStore;
    private final ReviewSubstringEngine reviewEngine;

    public M2DocumentEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
        this.reviewEngine = new ReviewSubstringEngine(dataStore);
    }

    // USE CASE 1
    public SimilarityResult compareProductDescriptions(String productIdA, String productIdB) {
        Product pA = null;
        Product pB = null;
        
        for (Product p : dataStore.getProducts()) {
            if (p.getProductId().equals(productIdA)) pA = p;
            if (p.getProductId().equals(productIdB)) pB = p;
        }
        
        if (pA == null || pB == null) return null;
        
        return DocumentSimilarityEngine.compare(pA.getDescription(), pB.getDescription());
    }

    // USE CASE 2
    public List<String> searchReviewSubstring(String productId, String phrase) {
        return reviewEngine.searchByProduct(productId, phrase);
    }

    // USE CASE 3
    public List<String> globalReviewCorpusSearch(String phrase) {
        StringBuilder sb = new StringBuilder();
        List<String> reviewIds = new ArrayList<>();
        List<Integer> boundaries = new ArrayList<>();
        
        for (Review r : dataStore.getReviews()) {
            String norm = TextNormalizer.normalize(r.getReviewText());
            boundaries.add(sb.length());
            reviewIds.add(r.getReviewId());
            sb.append(norm).append("$");
        }
        boundaries.add(sb.length()); // Add an end boundary for the last review
        
        SuffixArray sa = new SuffixArray(sb.toString());
        List<Integer> positions = sa.search(TextNormalizer.normalize(phrase));
        
        List<String> results = new ArrayList<>();
        for (int pos : positions) {
            // Find which review this position belongs to
            for (int i = 0; i < boundaries.size() - 1; i++) {
                if (pos >= boundaries.get(i) && pos < boundaries.get(i+1)) {
                    if (!results.contains(reviewIds.get(i))) {
                        results.add(reviewIds.get(i));
                    }
                    break;
                }
            }
        }
        return results;
    }

    // USE CASE 4
    public boolean checkAutomaton(String text, String phrase) {
        SuffixAutomaton sa = new SuffixAutomaton(TextNormalizer.normalize(text));
        return sa.contains(TextNormalizer.normalize(phrase));
    }

    // USE CASE 5
    public boolean checkSuffixTree(String text, String phrase) {
        SuffixTree st = new SuffixTree(TextNormalizer.normalize(text));
        return st.contains(TextNormalizer.normalize(phrase));
    }
}
