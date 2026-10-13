package core;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    
    private String basePath;
    
    // Legacy Phase 0 Fields
    private List<Product> products = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private List<Warehouse> warehouses = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();
    private List<Inventory> inventories = new ArrayList<>();
    private List<Activity> activities = new ArrayList<>();

    // Phase 0 Constructor
    public DataLoader() {
        this.basePath = null;
    }

    // Phase 1 Constructor
    public DataLoader(String basePath) {
        this.basePath = basePath;
    }

    // Phase 0 Methods
    public void loadSampleData() {
        // Load exactly 10 Products
        products.add(new Product("P001", "Galaxy S24 Ultra", "Smartphone", "High-end smartphone with excellent camera and stylus", 1199.99, 4.8, 100, "W1", 50));
        products.add(new Product("P002", "iPhone 15 Pro", "Smartphone", "Latest Apple smartphone with titanium body", 1099.99, 4.7, 80, "W1", 60));
        products.add(new Product("P003", "MacBook Pro M3", "Laptop", "Powerful laptop for professionals", 1999.99, 4.9, 40, "W2", 20));
        products.add(new Product("P004", "Dell XPS 15", "Laptop", "Premium Windows laptop with 4K display", 1799.99, 4.6, 50, "W2", 25));
        products.add(new Product("P005", "Sony WH-1000XM5", "Headphones", "Noise-cancelling over-ear headphones", 349.99, 4.8, 150, "W1", 100));
        products.add(new Product("P006", "Bose QuietComfort 45", "Headphones", "Comfortable active noise-cancelling headphones", 329.99, 4.7, 120, "W1", 80));
        products.add(new Product("P007", "Keychron Q1 Pro", "Keyboard", "Mechanical wireless keyboard with aluminum frame", 199.99, 4.8, 60, "W3", 30));
        products.add(new Product("P008", "Logitech MX Master 3S", "Accessories", "Ergonomic wireless mouse for productivity", 99.99, 4.9, 200, "W3", 150));
        products.add(new Product("P009", "Apple Watch Series 9", "Smart Watch", "Advanced health tracking smartwatch", 399.99, 4.7, 90, "W2", 70));
        products.add(new Product("P010", "LG UltraGear 27", "Monitor", "1440p 144Hz gaming monitor", 299.99, 4.6, 45, "W2", 35));

        // Load exactly 5 Customers
        customers.add(new Customer("C001", "Alice Smith"));
        customers.add(new Customer("C002", "Bob Johnson"));
        customers.add(new Customer("C003", "Charlie Brown"));
        customers.add(new Customer("C004", "Diana Prince"));
        customers.add(new Customer("C005", "Ethan Hunt"));

        // Load exactly 10 Transactions
        long currentTime = System.currentTimeMillis();
        transactions.add(new Transaction("T001", "C001", "P001", 1, 1199.99, currentTime - 100000));
        transactions.add(new Transaction("T002", "C002", "P005", 1, 349.99, currentTime - 90000));
        transactions.add(new Transaction("T003", "C003", "P003", 1, 1999.99, currentTime - 80000));
        transactions.add(new Transaction("T004", "C004", "P008", 2, 199.98, currentTime - 70000));
        transactions.add(new Transaction("T005", "C001", "P009", 1, 399.99, currentTime - 60000));
        transactions.add(new Transaction("T006", "C005", "P007", 1, 199.99, currentTime - 50000));
        transactions.add(new Transaction("T007", "C002", "P004", 1, 1799.99, currentTime - 40000));
        transactions.add(new Transaction("T008", "C003", "P006", 1, 329.99, currentTime - 30000));
        transactions.add(new Transaction("T009", "C004", "P002", 1, 1099.99, currentTime - 20000));
        transactions.add(new Transaction("T010", "C005", "P010", 1, 299.99, currentTime - 10000));

        // Load exactly 10 Reviews
        reviews.add(new Review("R001", "P001", "C001", "Amazing smartphone, best camera ever. Love the stylus.", 5.0));
        reviews.add(new Review("R002", "P002", "C004", "Great phone but a bit expensive. Titanium body is nice.", 4.5));
        reviews.add(new Review("R003", "P003", "C003", "Incredible performance for compiling code. Highly recommended laptop.", 5.0));
        reviews.add(new Review("R004", "P005", "C002", "Noise cancellation is fantastic. Best headphones for travel.", 5.0));
        reviews.add(new Review("R005", "P006", "C003", "Very comfortable headphones, but sound quality could be better.", 4.0));
        reviews.add(new Review("R006", "P007", "C005", "Best mechanical keyboard I have used. Good sound.", 4.8));
        reviews.add(new Review("R007", "P008", "C004", "Ergonomic and highly customizable mouse. Love the scroll wheel.", 5.0));
        reviews.add(new Review("R008", "P004", "C002", "Screen is beautiful. Good Windows laptop for work.", 4.5));
        reviews.add(new Review("R009", "P009", "C001", "Tracks workouts perfectly. Battery life is decent.", 4.2));
        reviews.add(new Review("R010", "P010", "C005", "Smooth gaming experience on this monitor. Great colors.", 4.6));

        // Load exactly 3 Warehouses
        warehouses.add(new Warehouse("W1", "New York", 5000));
        warehouses.add(new Warehouse("W2", "San Francisco", 4000));
        warehouses.add(new Warehouse("W3", "Chicago", 3000));

        // Load exactly 3 Stores
        stores.add(new Store("S1", "Manhattan", 500));
        stores.add(new Store("S2", "Silicon Valley", 600));
        stores.add(new Store("S3", "Downtown Chicago", 400));

        // Load exactly 10 Inventory records
        inventories.add(new Inventory("W1", "P001", 100));
        inventories.add(new Inventory("W1", "P002", 80));
        inventories.add(new Inventory("W1", "P005", 150));
        inventories.add(new Inventory("W1", "P006", 120));
        inventories.add(new Inventory("W2", "P003", 40));
        inventories.add(new Inventory("W2", "P004", 50));
        inventories.add(new Inventory("W2", "P009", 90));
        inventories.add(new Inventory("W2", "P010", 45));
        inventories.add(new Inventory("W3", "P007", 60));
        inventories.add(new Inventory("W3", "P008", 200));

        // Load exactly 10 Activities
        activities.add(new Activity("A001", "C001", "SEARCH", null, currentTime - 110000));
        activities.add(new Activity("A002", "C001", "VIEW", "P001", currentTime - 105000));
        activities.add(new Activity("A003", "C001", "ADD_TO_CART", "P001", currentTime - 102000));
        activities.add(new Activity("A004", "C002", "VIEW", "P005", currentTime - 95000));
        activities.add(new Activity("A005", "C002", "VIEW", "P006", currentTime - 92000));
        activities.add(new Activity("A006", "C003", "SEARCH", null, currentTime - 85000));
        activities.add(new Activity("A007", "C003", "VIEW", "P003", currentTime - 82000));
        activities.add(new Activity("A008", "C004", "VIEW", "P008", currentTime - 75000));
        activities.add(new Activity("A009", "C004", "ADD_TO_CART", "P008", currentTime - 72000));
        activities.add(new Activity("A010", "C005", "VIEW", "P010", currentTime - 15000));
    }

    public List<Product> getProducts() { return products; }
    public List<Customer> getCustomers() { return customers; }
    public List<Transaction> getTransactions() { return transactions; }
    public List<Review> getReviews() { return reviews; }
    public List<Warehouse> getWarehouses() { return warehouses; }
    public List<Store> getStores() { return stores; }
    public List<Inventory> getInventories() { return inventories; }
    public List<Activity> getActivities() { return activities; }

    // Phase 1 Method
    public RetailDataStore loadAll() {
        return new RetailDataStore(
            loadCsvProducts(),
            loadCsvCustomers(),
            loadCsvTransactions(),
            loadCsvReviews(),
            loadCsvWarehouses(),
            loadCsvStores(),
            loadCsvInventory(),
            loadCsvActivities()
        );
    }

    private List<Product> loadCsvProducts() {
        List<Product> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/products/products.csv");
        for (String[] r : records) {
            if (r.length < 9) continue;
            try {
                list.add(new Product(r[0], r[1], r[2], r[3], Double.parseDouble(r[4]), 
                    Double.parseDouble(r[5]), Integer.parseInt(r[6]), r[7], Integer.parseInt(r[8])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Customer> loadCsvCustomers() {
        List<Customer> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/customers/customers.csv");
        for (String[] r : records) {
            if (r.length < 2) continue;
            list.add(new Customer(r[0], r[1]));
        }
        return list;
    }

    private List<Transaction> loadCsvTransactions() {
        List<Transaction> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/transactions/transactions.csv");
        for (String[] r : records) {
            if (r.length < 6) continue;
            try {
                list.add(new Transaction(r[0], r[1], r[2], Integer.parseInt(r[3]), Double.parseDouble(r[4]), Long.parseLong(r[5])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Review> loadCsvReviews() {
        List<Review> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/reviews/reviews.csv");
        for (String[] r : records) {
            if (r.length < 5) continue;
            try {
                list.add(new Review(r[0], r[1], r[2], r[3], Double.parseDouble(r[4])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Warehouse> loadCsvWarehouses() {
        List<Warehouse> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/warehouses/warehouses.csv");
        for (String[] r : records) {
            if (r.length < 3) continue;
            try {
                list.add(new Warehouse(r[0], r[1], Integer.parseInt(r[2])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Store> loadCsvStores() {
        List<Store> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/stores/stores.csv");
        for (String[] r : records) {
            if (r.length < 3) continue;
            try {
                list.add(new Store(r[0], r[1], Integer.parseInt(r[2])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Inventory> loadCsvInventory() {
        List<Inventory> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/inventory/inventory.csv");
        for (String[] r : records) {
            if (r.length < 3) continue;
            try {
                list.add(new Inventory(r[0], r[1], Integer.parseInt(r[2])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }

    private List<Activity> loadCsvActivities() {
        List<Activity> list = new ArrayList<>();
        if (basePath == null) return list;
        List<String[]> records = CsvReader.readCsv(basePath + "/activities/activities.csv");
        for (String[] r : records) {
            if (r.length < 5) continue;
            try {
                list.add(new Activity(r[0], r[1], r[2], (r[3] == null || r[3].isEmpty()) ? null : r[3], Long.parseLong(r[4])));
            } catch (Exception e) {
                // Ignore malformed for resilience
            }
        }
        return list;
    }
}

