package core;

import repository.*;

public class QueryProcessor {
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final ReviewRepository reviewRepo;
    private final TransactionRepository transactionRepo;
    private final WarehouseRepository warehouseRepo;
    private final StoreRepository storeRepo;
    private final InventoryRepository inventoryRepo;
    private final ActivityRepository activityRepo;

    public QueryProcessor(
            ProductRepository productRepo,
            CustomerRepository customerRepo,
            ReviewRepository reviewRepo,
            TransactionRepository transactionRepo,
            WarehouseRepository warehouseRepo,
            StoreRepository storeRepo,
            InventoryRepository inventoryRepo,
            ActivityRepository activityRepo) {
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.reviewRepo = reviewRepo;
        this.transactionRepo = transactionRepo;
        this.warehouseRepo = warehouseRepo;
        this.storeRepo = storeRepo;
        this.inventoryRepo = inventoryRepo;
        this.activityRepo = activityRepo;
    }

    public Object process(Query query) {
        if (query == null || query.getOperation() == null) {
            return "Invalid query.";
        }
        
        switch (query.getOperation()) {
            case GET_PRODUCT: return productRepo.findById(query.getProductId());
            case GET_CUSTOMER: return customerRepo.findById(query.getCustomerId());
            case GET_REVIEW: return reviewRepo.findById(query.getReviewId());
            case GET_TRANSACTION: return transactionRepo.findById(query.getTransactionId());
            case GET_WAREHOUSE: return warehouseRepo.findById(query.getWarehouseId());
            case GET_STORE: return storeRepo.findById(query.getCategory()); 
            case GET_PRODUCT_REVIEWS: return reviewRepo.findByProductId(query.getProductId());
            case GET_CUSTOMER_TRANSACTIONS: return transactionRepo.findByCustomerId(query.getCustomerId());
            case GET_PRODUCT_TRANSACTIONS: return transactionRepo.findByProductId(query.getProductId());
            case GET_INVENTORY:
            case GET_PRODUCT_INVENTORY: return inventoryRepo.findByProductId(query.getProductId());
            case GET_WAREHOUSE_INVENTORY: return inventoryRepo.findByWarehouseId(query.getWarehouseId());
            case GET_CUSTOMER_ACTIVITIES: return activityRepo.findByCustomerId(query.getCustomerId());
            default: return "Unknown operation.";
        }
    }
}
