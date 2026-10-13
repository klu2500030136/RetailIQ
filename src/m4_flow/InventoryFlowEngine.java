package m4_flow;

import core.RetailDataStore;
import model.Inventory;
import model.Store;
import model.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryFlowEngine {
    private final RetailDataStore dataStore;

    public InventoryFlowEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public InventoryFlowResult planProductShipments(String productId) {
        List<Warehouse> warehouses = dataStore.getWarehouses();
        List<Store> stores = dataStore.getStores();
        int n = warehouses.size();
        int m = stores.size();
        int source = 0;
        int warehouseStart = 1;
        int storeStart = warehouseStart + n;
        int sink = storeStart + m;
        FlowNetwork network = new FlowNetwork(sink + 1);
        Map<String, Integer> productInventory = inventoryByWarehouse(productId);
        int[] supplies = new int[n];
        int[] demands = new int[m];

        int totalSupply = 0;
        int totalDemand = 0;
        for (int i = 0; i < n; i++) {
            int supply = productInventory.containsKey(warehouses.get(i).getWarehouseId())
                    ? productInventory.get(warehouses.get(i).getWarehouseId()) : 0;
            supplies[i] = supply;
            totalSupply += supply;
            if (supply > 0) network.addEdge(source, warehouseStart + i, supply);
        }
        for (int j = 0; j < m; j++) {
            int demand = Math.max(0, stores.get(j).getDemand());
            demands[j] = demand;
            totalDemand += demand;
            if (demand > 0) network.addEdge(storeStart + j, sink, demand);
        }
        for (int i = 0; i < n; i++) {
            if (supplies[i] == 0) continue;
            for (int j = 0; j < m; j++) {
                if (demands[j] == 0) continue;
                network.addEdge(warehouseStart + i, storeStart + j, Math.min(supplies[i], demands[j]));
            }
        }

        MaxFlowResult flow = Dinic.maxFlow(network, source, sink);
        List<ShipmentPlan> shipments = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (FlowNetwork.Edge edge : network.edgesFrom(warehouseStart + i)) {
                if (edge.to >= storeStart && edge.to < storeStart + m && edge.flow > 0) {
                    shipments.add(new ShipmentPlan(
                            warehouses.get(i).getWarehouseId(),
                            stores.get(edge.to - storeStart).getStoreId(),
                            edge.flow));
                }
            }
        }
        return new InventoryFlowResult(productId, totalSupply, totalDemand, flow.maxFlow, shipments);
    }

    private Map<String, Integer> inventoryByWarehouse(String productId) {
        Map<String, Integer> inventoryByWarehouse = new HashMap<>();
        for (Inventory inventory : dataStore.getInventories()) {
            if (inventory.getProductId().equals(productId)) {
                int quantity = Math.max(0, inventory.getQuantity());
                Integer current = inventoryByWarehouse.get(inventory.getWarehouseId());
                inventoryByWarehouse.put(inventory.getWarehouseId(), current == null ? quantity : current + quantity);
            }
        }
        return inventoryByWarehouse;
    }
}
