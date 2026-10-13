package model;

public class Review {
    private String reviewId;
    private String productId;
    private String customerId;
    private String reviewText;
    private double rating;

    public Review(String reviewId, String productId, String customerId, String reviewText, double rating) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + reviewId + '\'' +
                ", productId='" + productId + '\'' +
                ", rating=" + rating +
                '}';
    }
}
