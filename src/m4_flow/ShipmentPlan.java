package m4_flow;

public class ShipmentPlan {
    public final String warehouseId;
    public final String storeId;
    public final int quantity;

    public ShipmentPlan(String warehouseId, String storeId, int quantity) {
        this.warehouseId = warehouseId;
        this.storeId = storeId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return warehouseId + " -> " + storeId + " : " + quantity;
    }
}
