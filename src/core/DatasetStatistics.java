package core;

import repository.*;
import model.Transaction;
import model.Product;

public class DatasetStatistics {
    
    public static void printSummary(
            ProductRepository prodRepo, 
            CustomerRepository custRepo, 
            ReviewRepository revRepo, 
            TransactionRepository txRepo,
            WarehouseRepository whRepo,
            StoreRepository storeRepo,
            InventoryRepository invRepo,
            ActivityRepository actRepo) {
        
        ResultFormatter.printHeader("DATASET SUMMARY");
        ResultFormatter.printItem("Products", prodRepo.count());
        ResultFormatter.printItem("Customers", custRepo.count());
        ResultFormatter.printItem("Reviews", revRepo.count());
        ResultFormatter.printItem("Transactions", txRepo.count());
        ResultFormatter.printItem("Warehouses", whRepo.count());
        ResultFormatter.printItem("Stores", storeRepo.count());
        ResultFormatter.printItem("Inventory", invRepo.count());
        ResultFormatter.printItem("Activities", actRepo.count());
        
        double totalTxValue = 0;
        int totalUnits = 0;
        for (Transaction t : txRepo.findAll()) {
            totalTxValue += t.getAmount();
            totalUnits += t.getQuantity();
        }
        
        double totalRating = 0;
        for (Product p : prodRepo.findAll()) {
            totalRating += p.getRating();
        }
        double avgRating = prodRepo.count() > 0 ? totalRating / prodRepo.count() : 0.0;
        
        ResultFormatter.printHeader("STATISTICS");
        ResultFormatter.printItem("Total Tx Value", String.format("$%.2f", totalTxValue));
        ResultFormatter.printItem("Total Units Sold", totalUnits);
        ResultFormatter.printItem("Avg Prod Rating", String.format("%.2f", avgRating));
        ResultFormatter.printHeader("");
    }
}
