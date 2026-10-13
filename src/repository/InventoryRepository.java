package repository;

import model.Inventory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryRepository {
    private final List<Inventory> inventories;
    private final Map<String, List<Inventory>> productIndex;
    private final Map<String, List<Inventory>> warehouseIndex;

    public InventoryRepository(List<Inventory> inventories) {
        this.inventories = inventories;
        this.productIndex = new HashMap<>();
        this.warehouseIndex = new HashMap<>();
        for (Inventory i : inventories) {
            productIndex.computeIfAbsent(i.getProductId(), k -> new ArrayList<>()).add(i);
            warehouseIndex.computeIfAbsent(i.getWarehouseId(), k -> new ArrayList<>()).add(i);
        }
    }

    public List<Inventory> findByProductId(String productId) {
        return productIndex.getOrDefault(productId, new ArrayList<>());
    }

    public List<Inventory> findByWarehouseId(String warehouseId) {
        return warehouseIndex.getOrDefault(warehouseId, new ArrayList<>());
    }

    public List<Inventory> findAll() {
        return inventories;
    }

    public int count() {
        return inventories.size();
    }
}
