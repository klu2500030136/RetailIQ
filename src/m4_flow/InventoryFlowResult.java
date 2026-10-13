package m4_flow;

import java.util.List;

public class InventoryFlowResult {
    public final String productId;
    public final int totalSupply;
    public final int totalDemand;
    public final int fulfilledDemand;
    public final List<ShipmentPlan> shipments;

    public InventoryFlowResult(String productId, int totalSupply, int totalDemand, int fulfilledDemand, List<ShipmentPlan> shipments) {
        this.productId = productId;
        this.totalSupply = totalSupply;
        this.totalDemand = totalDemand;
        this.fulfilledDemand = fulfilledDemand;
        this.shipments = shipments;
    }
}
