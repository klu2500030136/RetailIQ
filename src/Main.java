
import core.DataLoader;
import core.DataValidator;
import core.RetailDataStore;
import cli.RetailCLI;
import repository.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("                       RETAILIQ");
        System.out.println("          Intelligent Retail Analytics Platform");
        System.out.println("============================================================");
        System.out.println();
        System.out.println("Loading RetailIQ datasets...");
        System.out.println();
        DataLoader loader = new DataLoader("data");
        RetailDataStore dataStore = loader.loadAll();
        System.out.printf("%-16s: %d%n", "Products",     dataStore.getProducts().size());
        System.out.printf("%-16s: %d%n", "Customers",    dataStore.getCustomers().size());
        System.out.printf("%-16s: %d%n", "Transactions", dataStore.getTransactions().size());
        System.out.printf("%-16s: %d%n", "Reviews",      dataStore.getReviews().size());
        System.out.printf("%-16s: %d%n", "Warehouses",   dataStore.getWarehouses().size());
        System.out.printf("%-16s: %d%n", "Stores",       dataStore.getStores().size());
        System.out.printf("%-16s: %d%n", "Inventory",    dataStore.getInventories().size());
        System.out.printf("%-16s: %d%n", "Activities",   dataStore.getActivities().size());
        System.out.println();
        DataValidator.validate(
            new ProductRepository(dataStore.getProducts()),
            new CustomerRepository(dataStore.getCustomers()),
            new ReviewRepository(dataStore.getReviews()),
            new TransactionRepository(dataStore.getTransactions()),
            new WarehouseRepository(dataStore.getWarehouses()),
            new StoreRepository(dataStore.getStores()),
            new InventoryRepository(dataStore.getInventories()),
            new ActivityRepository(dataStore.getActivities())
        );
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("Algorithm Engines");
        System.out.println("------------------------------------------------------------");
        System.out.println("M1 String Algorithms       : READY");
        System.out.println("M2 Suffix Structures       : READY");
        System.out.println("M3 Advanced DP             : READY");
        System.out.println("M4 Network Flow            : READY");
        System.out.println("M5 NP/Approximation        : READY");
        System.out.println("M6 Randomized & Parallel   : READY");
        System.out.println("------------------------------------------------------------");
        System.out.println();
        System.out.println("RetailIQ initialized. Press ENTER to open the main menu...");
        try { System.in.read(); } catch (Exception ignored) {}
        new RetailCLI(dataStore).start();
    }
}
