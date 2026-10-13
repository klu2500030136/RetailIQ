package repository;

import model.Customer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepository {
    private final List<Customer> customers;
    private final Map<String, Customer> index;

    public CustomerRepository(List<Customer> customers) {
        this.customers = customers;
        this.index = new HashMap<>();
        for (Customer c : customers) {
            index.put(c.getCustomerId(), c);
        }
    }

    public Customer findById(String customerId) {
        return index.get(customerId);
    }

    public List<Customer> findAll() {
        return customers;
    }

    public int count() {
        return customers.size();
    }
}
