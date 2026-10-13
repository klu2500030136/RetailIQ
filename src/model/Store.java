package model;

public class Store {
    private String storeId;
    private String location;
    private int demand;

    public Store(String storeId, String location, int demand) {
        this.storeId = storeId;
        this.location = location;
        this.demand = demand;
    }

    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getDemand() { return demand; }
    public void setDemand(int demand) { this.demand = demand; }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + storeId + '\'' +
                ", location='" + location + '\'' +
                ", demand=" + demand +
                '}';
    }
}
