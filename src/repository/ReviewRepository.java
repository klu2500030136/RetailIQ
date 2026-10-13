package repository;

import model.Review;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewRepository {
    private final List<Review> reviews;
    private final Map<String, Review> idIndex;
    private final Map<String, List<Review>> productIndex;

    public ReviewRepository(List<Review> reviews) {
        this.reviews = reviews;
        this.idIndex = new HashMap<>();
        this.productIndex = new HashMap<>();
        for (Review r : reviews) {
            idIndex.put(r.getReviewId(), r);
            productIndex.computeIfAbsent(r.getProductId(), k -> new ArrayList<>()).add(r);
        }
    }

    public Review findById(String reviewId) {
        return idIndex.get(reviewId);
    }

    public List<Review> findByProductId(String productId) {
        return productIndex.getOrDefault(productId, new ArrayList<>());
    }

    public List<Review> findAll() {
        return reviews;
    }

    public int count() {
        return reviews.size();
    }
}
