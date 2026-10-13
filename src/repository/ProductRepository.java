package repository;

import model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private final List<Product> products;
    private final Map<String, Product> index;

    public ProductRepository(List<Product> products) {
        this.products = products;
        this.index = new HashMap<>();
        for (Product p : products) {
            index.put(p.getProductId(), p);
        }
    }

    public Product findById(String productId) {
        return index.get(productId);
    }

    public List<Product> findAll() {
        return products;
    }

    public int count() {
        return products.size();
    }
}
