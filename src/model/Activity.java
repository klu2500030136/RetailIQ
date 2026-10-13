package model;

public class Activity {
    private String activityId;
    private String customerId;
    private String activityType; // e.g., "VIEW", "SEARCH", "ADD_TO_CART"
    private String productId;
    private long timestamp;

    public Activity(String activityId, String customerId, String activityType, String productId, long timestamp) {
        this.activityId = activityId;
        this.customerId = customerId;
        this.activityType = activityType;
        this.productId = productId;
        this.timestamp = timestamp;
    }

    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + activityId + '\'' +
                ", type='" + activityType + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
