package core;

import repository.*;
import model.*;

public class DataValidator {
    
    public static boolean validate(
            ProductRepository prodRepo, 
            CustomerRepository custRepo, 
            ReviewRepository revRepo, 
            TransactionRepository txRepo,
            WarehouseRepository whRepo,
            StoreRepository storeRepo,
            InventoryRepository invRepo,
            ActivityRepository actRepo) {
        
        System.out.println("Validating data integrity...");
        boolean isValid = true;
        
        // Validate Transactions
        for (Transaction t : txRepo.findAll()) {
            if (custRepo.findById(t.getCustomerId()) == null) {
                System.err.println("Invalid customerId in transaction: " + t.getTransactionId());
                isValid = false;
            }
            if (prodRepo.findById(t.getProductId()) == null) {
                System.err.println("Invalid productId in transaction: " + t.getTransactionId());
                isValid = false;
            }
            if (t.getAmount() < 0 || t.getQuantity() < 0) {
                System.err.println("Negative amount/quantity in transaction: " + t.getTransactionId());
                isValid = false;
            }
        }
        
        // Validate Reviews
        for (Review r : revRepo.findAll()) {
            if (prodRepo.findById(r.getProductId()) == null) {
                System.err.println("Invalid productId in review: " + r.getReviewId());
                isValid = false;
            }
            if (custRepo.findById(r.getCustomerId()) == null) {
                System.err.println("Invalid customerId in review: " + r.getReviewId());
                isValid = false;
            }
            if (r.getRating() < 0.0 || r.getRating() > 5.0) {
                System.err.println("Invalid rating in review: " + r.getReviewId());
                isValid = false;
            }
        }
        
        // Validate Inventory
        for (Inventory i : invRepo.findAll()) {
            if (whRepo.findById(i.getWarehouseId()) == null) {
                System.err.println("Invalid warehouseId in inventory");
                isValid = false;
            }
            if (prodRepo.findById(i.getProductId()) == null) {
                System.err.println("Invalid productId in inventory");
                isValid = false;
            }
            if (i.getQuantity() < 0) {
                System.err.println("Negative quantity in inventory");
                isValid = false;
            }
        }
        
        // Validate Activities
        for (Activity a : actRepo.findAll()) {
            if (custRepo.findById(a.getCustomerId()) == null) {
                System.err.println("Invalid customerId in activity: " + a.getActivityId());
                isValid = false;
            }
            if (a.getProductId() != null && !a.getProductId().isEmpty()) {
                if (prodRepo.findById(a.getProductId()) == null) {
                    System.err.println("Invalid productId in activity: " + a.getActivityId());
                    isValid = false;
                }
            }
        }
        
        // Validate Products
        for (Product p : prodRepo.findAll()) {
            if (p.getPrice() < 0) {
                System.err.println("Negative price in product: " + p.getProductId());
                isValid = false;
            }
        }

        if (isValid) {
            System.out.println("Data validation: PASSED");
        } else {
            System.out.println("Data validation: FAILED");
        }
        
        return isValid;
    }
}
