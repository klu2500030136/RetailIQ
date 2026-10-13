package repository;

import model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private final List<Transaction> transactions;
    private final Map<String, Transaction> idIndex;
    private final Map<String, List<Transaction>> customerIndex;
    private final Map<String, List<Transaction>> productIndex;

    public TransactionRepository(List<Transaction> transactions) {
        this.transactions = transactions;
        this.idIndex = new HashMap<>();
        this.customerIndex = new HashMap<>();
        this.productIndex = new HashMap<>();
        for (Transaction t : transactions) {
            idIndex.put(t.getTransactionId(), t);
            customerIndex.computeIfAbsent(t.getCustomerId(), k -> new ArrayList<>()).add(t);
            productIndex.computeIfAbsent(t.getProductId(), k -> new ArrayList<>()).add(t);
        }
    }

    public Transaction findById(String transactionId) {
        return idIndex.get(transactionId);
    }

    public List<Transaction> findByCustomerId(String customerId) {
        return customerIndex.getOrDefault(customerId, new ArrayList<>());
    }
    
    public List<Transaction> findByProductId(String productId) {
        return productIndex.getOrDefault(productId, new ArrayList<>());
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public int count() {
        return transactions.size();
    }
}
