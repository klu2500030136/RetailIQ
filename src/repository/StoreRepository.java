package repository;

import model.Store;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreRepository {
    private final List<Store> stores;
    private final Map<String, Store> index;

    public StoreRepository(List<Store> stores) {
        this.stores = stores;
        this.index = new HashMap<>();
        for (Store s : stores) {
            index.put(s.getStoreId(), s);
        }
    }

    public Store findById(String storeId) {
        return index.get(storeId);
    }

    public List<Store> findAll() {
        return stores;
    }

    public int count() {
        return stores.size();
    }
}
