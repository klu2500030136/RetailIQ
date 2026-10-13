package repository;

import model.Warehouse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseRepository {
    private final List<Warehouse> warehouses;
    private final Map<String, Warehouse> index;

    public WarehouseRepository(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
        this.index = new HashMap<>();
        for (Warehouse w : warehouses) {
            index.put(w.getWarehouseId(), w);
        }
    }

    public Warehouse findById(String warehouseId) {
        return index.get(warehouseId);
    }

    public List<Warehouse> findAll() {
        return warehouses;
    }

    public int count() {
        return warehouses.size();
    }
}
