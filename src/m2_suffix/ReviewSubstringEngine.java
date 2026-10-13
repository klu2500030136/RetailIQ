package m2_suffix;

import core.RetailDataStore;
import model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewSubstringEngine {
    
    private final RetailDataStore dataStore;
    // Map of ProductId -> SuffixArray of combined reviews
    private final Map<String, SuffixArray> productReviewIndices;
    // Map of ProductId -> List of review boundaries (to map back to review IDs)
    private final Map<String, List<ReviewBoundary>> productReviewBoundaries;

    private static class ReviewBoundary {
        String reviewId;
        int startIndex;
        int endIndex;
        ReviewBoundary(String id, int s, int e) { reviewId = id; startIndex = s; endIndex = e; }
    }

    public ReviewSubstringEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
        this.productReviewIndices = new HashMap<>();
        this.productReviewBoundaries = new HashMap<>();
    }

    public void buildIndex(String productId) {
        if (productReviewIndices.containsKey(productId)) return;
        
        StringBuilder sb = new StringBuilder();
        List<ReviewBoundary> boundaries = new ArrayList<>();
        
        for (Review r : dataStore.getReviews()) {
            if (r.getProductId().equals(productId)) {
                String norm = TextNormalizer.normalize(r.getReviewText());
                int start = sb.length();
                sb.append(norm).append("$");
                int end = sb.length() - 1; // don't include separator
                
                boundaries.add(new ReviewBoundary(r.getReviewId(), start, end));
            }
        }
        
        String combined = sb.toString();
        if (combined.length() > 0) {
            productReviewIndices.put(productId, new SuffixArray(combined));
            productReviewBoundaries.put(productId, boundaries);
        }
    }

    public List<String> searchByProduct(String productId, String query) {
        buildIndex(productId);
        List<String> results = new ArrayList<>();
        
        SuffixArray sa = productReviewIndices.get(productId);
        if (sa == null) return results;
        
        String normQuery = TextNormalizer.normalize(query);
        List<Integer> positions = sa.search(normQuery);
        
        List<ReviewBoundary> boundaries = productReviewBoundaries.get(productId);
        for (int pos : positions) {
            for (ReviewBoundary b : boundaries) {
                if (pos >= b.startIndex && pos <= b.endIndex) {
                    if (!results.contains(b.reviewId)) {
                        results.add(b.reviewId);
                    }
                    break;
                }
            }
        }
        return results;
    }
}
