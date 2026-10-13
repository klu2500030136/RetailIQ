package tests;

import core.DataLoader;
import model.*;

public class Phase0Tests {

    public static void main(String[] args) {
        System.out.println("Running Phase 0 Foundation Tests...\n");
        int passed = 0;
        int failed = 0;

        try {
            testProductCreation();
            System.out.println("[PASS] testProductCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testProductCreation: " + e.getMessage());
            failed++;
        }

        try {
            testCustomerCreation();
            System.out.println("[PASS] testCustomerCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testCustomerCreation: " + e.getMessage());
            failed++;
        }

        try {
            testTransactionCreation();
            System.out.println("[PASS] testTransactionCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testTransactionCreation: " + e.getMessage());
            failed++;
        }
        
        try {
            testReviewCreation();
            System.out.println("[PASS] testReviewCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testReviewCreation: " + e.getMessage());
            failed++;
        }

        try {
            testWarehouseCreation();
            System.out.println("[PASS] testWarehouseCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testWarehouseCreation: " + e.getMessage());
            failed++;
        }

        try {
            testStoreCreation();
            System.out.println("[PASS] testStoreCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testStoreCreation: " + e.getMessage());
            failed++;
        }
        
        try {
            testInventoryCreation();
            System.out.println("[PASS] testInventoryCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testInventoryCreation: " + e.getMessage());
            failed++;
        }

        try {
            testActivityCreation();
            System.out.println("[PASS] testActivityCreation");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testActivityCreation: " + e.getMessage());
            failed++;
        }

        try {
            testDataLoading();
            System.out.println("[PASS] testDataLoading");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testDataLoading: " + e.getMessage());
            failed++;
        }

        System.out.println("\nTests Complete: " + passed + " Passed, " + failed + " Failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testProductCreation() {
        Product p = new Product("P1", "Test Product", "Test Category", "Description", 9.99, 4.5, 10, "W1", 5);
        if (!p.getProductId().equals("P1")) throw new RuntimeException("ID mismatch");
    }

    private static void testCustomerCreation() {
        Customer c = new Customer("C1", "Test Customer");
        if (!c.getCustomerId().equals("C1")) throw new RuntimeException("ID mismatch");
    }

    private static void testTransactionCreation() {
        Transaction t = new Transaction("T1", "C1", "P1", 2, 19.98, System.currentTimeMillis());
        if (!t.getTransactionId().equals("T1")) throw new RuntimeException("ID mismatch");
    }

    private static void testReviewCreation() {
        Review r = new Review("R1", "P1", "C1", "Great!", 5.0);
        if (!r.getReviewId().equals("R1")) throw new RuntimeException("ID mismatch");
    }
    
    private static void testWarehouseCreation() {
        Warehouse w = new Warehouse("W1", "Location", 1000);
        if (!w.getWarehouseId().equals("W1")) throw new RuntimeException("ID mismatch");
    }
    
    private static void testStoreCreation() {
        Store s = new Store("S1", "Location", 100);
        if (!s.getStoreId().equals("S1")) throw new RuntimeException("ID mismatch");
    }
    
    private static void testInventoryCreation() {
        Inventory i = new Inventory("W1", "P1", 50);
        if (!i.getWarehouseId().equals("W1") || !i.getProductId().equals("P1")) throw new RuntimeException("ID mismatch");
    }
    
    private static void testActivityCreation() {
        Activity a = new Activity("A1", "C1", "VIEW", "P1", System.currentTimeMillis());
        if (!a.getActivityId().equals("A1")) throw new RuntimeException("ID mismatch");
    }

    private static void testDataLoading() {
        DataLoader loader = new DataLoader();
        loader.loadSampleData();
        if (loader.getProducts().size() != 10) throw new RuntimeException("Expected 10 products, got " + loader.getProducts().size());
        if (loader.getCustomers().size() != 5) throw new RuntimeException("Expected 5 customers, got " + loader.getCustomers().size());
        if (loader.getTransactions().size() != 10) throw new RuntimeException("Expected 10 transactions");
        if (loader.getReviews().size() != 10) throw new RuntimeException("Expected 10 reviews");
        if (loader.getWarehouses().size() != 3) throw new RuntimeException("Expected 3 warehouses");
        if (loader.getStores().size() != 3) throw new RuntimeException("Expected 3 stores");
        if (loader.getInventories().size() != 10) throw new RuntimeException("Expected 10 inventory records");
        if (loader.getActivities().size() != 10) throw new RuntimeException("Expected 10 activities");
    }
}
