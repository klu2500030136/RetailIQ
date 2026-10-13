package core;

import model.*;
import java.util.List;

public class RetailDataStore {
    private List<Product> products;
    private List<Customer> customers;
    private List<Transaction> transactions;
    private List<Review> reviews;
    private List<Warehouse> warehouses;
    private List<Store> stores;
    private List<Inventory> inventories;
    private List<Activity> activities;

    public RetailDataStore(
            List<Product> products,
            List<Customer> customers,
            List<Transaction> transactions,
            List<Review> reviews,
            List<Warehouse> warehouses,
            List<Store> stores,
            List<Inventory> inventories,
            List<Activity> activities) {
        this.products = products;
        this.customers = customers;
        this.transactions = transactions;
        this.reviews = reviews;
        this.warehouses = warehouses;
        this.stores = stores;
        this.inventories = inventories;
        this.activities = activities;
    }

    public List<Product> getProducts() { return products; }
    public List<Customer> getCustomers() { return customers; }
    public List<Transaction> getTransactions() { return transactions; }
    public List<Review> getReviews() { return reviews; }
    public List<Warehouse> getWarehouses() { return warehouses; }
    public List<Store> getStores() { return stores; }
    public List<Inventory> getInventories() { return inventories; }
    public List<Activity> getActivities() { return activities; }
}
