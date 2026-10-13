package tests;

import core.*;
import repository.*;
import model.*;
import java.util.List;

public class Phase1Tests {

    public static void main(String[] args) {
        System.out.println("Running Phase 1 Core/Data Engine Tests...\n");
        int passed = 0;
        int failed = 0;

        DataLoader loader = new DataLoader("data");
        RetailDataStore dataStore = loader.loadAll();

        ProductRepository prodRepo = new ProductRepository(dataStore.getProducts());
        CustomerRepository custRepo = new CustomerRepository(dataStore.getCustomers());
        ReviewRepository revRepo = new ReviewRepository(dataStore.getReviews());
        TransactionRepository txRepo = new TransactionRepository(dataStore.getTransactions());
        WarehouseRepository whRepo = new WarehouseRepository(dataStore.getWarehouses());
        StoreRepository storeRepo = new StoreRepository(dataStore.getStores());
        InventoryRepository invRepo = new InventoryRepository(dataStore.getInventories());
        ActivityRepository actRepo = new ActivityRepository(dataStore.getActivities());

        // Test Data Loading & Repositories
        try {
            if (prodRepo.count() > 0) passed++; else throw new Exception("Empty products");
            System.out.println("[PASS] Products load");
        } catch (Exception e) { System.out.println("[FAIL] Products load: " + e.getMessage()); failed++; }

        try {
            if (custRepo.count() > 0) passed++; else throw new Exception("Empty customers");
            System.out.println("[PASS] Customers load");
        } catch (Exception e) { System.out.println("[FAIL] Customers load: " + e.getMessage()); failed++; }
        
        try {
            if (txRepo.count() > 0) passed++; else throw new Exception("Empty tx");
            System.out.println("[PASS] Transactions load");
        } catch (Exception e) { System.out.println("[FAIL] Transactions load: " + e.getMessage()); failed++; }

        // Test ID Lookups
        try {
            Product p = prodRepo.findById("P0001");
            if (p != null && p.getProductId().equals("P0001")) passed++; else throw new Exception("Lookup failed");
            System.out.println("[PASS] Product findById");
        } catch (Exception e) { System.out.println("[FAIL] Product findById: " + e.getMessage()); failed++; }

        // Test Query Processor
        QueryProcessor qp = new QueryProcessor(prodRepo, custRepo, revRepo, txRepo, whRepo, storeRepo, invRepo, actRepo);
        try {
            Query q = new Query(QueryOperation.GET_PRODUCT);
            q.setProductId("P0001");
            Product p = (Product) qp.process(q);
            if (p != null) passed++; else throw new Exception("QP failed");
            System.out.println("[PASS] GET_PRODUCT via QueryProcessor");
        } catch (Exception e) { System.out.println("[FAIL] GET_PRODUCT: " + e.getMessage()); failed++; }

        try {
            Query q = new Query(QueryOperation.GET_CUSTOMER_TRANSACTIONS);
            q.setCustomerId("C0001");
            List<?> list = (List<?>) qp.process(q);
            if (list != null) passed++; else throw new Exception("QP failed");
            System.out.println("[PASS] GET_CUSTOMER_TRANSACTIONS via QueryProcessor");
        } catch (Exception e) { System.out.println("[FAIL] GET_CUSTOMER_TRANSACTIONS: " + e.getMessage()); failed++; }

        // Test Validation
        try {
            boolean valid = DataValidator.validate(prodRepo, custRepo, revRepo, txRepo, whRepo, storeRepo, invRepo, actRepo);
            if (valid) passed++; else throw new Exception("Validation failed");
            System.out.println("[PASS] Valid dataset passes");
        } catch (Exception e) { System.out.println("[FAIL] Valid dataset passes: " + e.getMessage()); failed++; }
        
        System.out.println("\nTests Complete: " + passed + " Passed, " + failed + " Failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
