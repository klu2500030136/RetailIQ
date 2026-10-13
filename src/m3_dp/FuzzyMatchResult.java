package m3_dp;

import model.Product;

public class FuzzyMatchResult implements Comparable<FuzzyMatchResult> {
    public final Product product;
    public final int distance;
    public final String algorithm;
    public final String matchedName;

    public FuzzyMatchResult(Product product, int distance, String algorithm, String matchedName) {
        this.product = product;
        this.distance = distance;
        this.algorithm = algorithm;
        this.matchedName = matchedName;
    }

    @Override
    public int compareTo(FuzzyMatchResult other) {
        if (this.distance != other.distance) {
            return Integer.compare(this.distance, other.distance);
        }
        // Deterministic secondary sort: shorter name first
        if (this.matchedName.length() != other.matchedName.length()) {
            return Integer.compare(this.matchedName.length(), other.matchedName.length());
        }
        return this.product.getProductId().compareTo(other.product.getProductId());
    }
}
