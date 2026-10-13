package cli;

import core.RetailDataStore;
import model.Customer;
import model.Product;
import model.Transaction;

public class CustomerAnalysisMenu {
    private final InputHelper input;
    private final RetailDataStore ds;
    public CustomerAnalysisMenu(InputHelper input, RetailDataStore ds) { this.input=input; this.ds=ds; }
    public void show() {
        while (true) {
            System.out.println("\n========== CUSTOMER & TRANSACTION ANALYSIS ==========");
            System.out.println("1. Customer Transaction History");
            System.out.println("2. Transaction Details by ID");
            System.out.println("3. Customer Spending Summary");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 3);
            if (c == 0) return;
            switch (c) {
                case 1: txHistory();    break;
                case 2: txById();       break;
                case 3: spendSummary(); break;
            }
        }
    }
    private void txHistory() {
        String cId = input.readString("Customer ID (e.g. C0001): ");
        Customer c = findC(cId);
        if (c==null) { System.out.println("Not found: "+cId); input.pause(); return; }
        System.out.println("\nCustomer: "+c.getName()+" ("+cId+")");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-14s %-10s %-8s %s%n","TxnID","ProductID","Qty","Amount");
        System.out.println("------------------------------------------------------------");
        int count=0; double total=0;
        for (Transaction t : ds.getTransactions()) {
            if (t.getCustomerId().equals(cId)) {
                System.out.printf("%-14s %-10s %-8d $%.2f%n",t.getTransactionId(),t.getProductId(),t.getQuantity(),t.getAmount());
                count++; total+=t.getAmount();
            }
        }
        if (count==0) System.out.println("No transactions found.");
        else { System.out.println("------------------------------------------------------------"); System.out.printf("Total: %d txn(s)  $%.2f%n",count,total); }
        input.pause();
    }
    private void txById() {
        String tId = input.readString("Transaction ID (e.g. T00001): ");
        Transaction t = findT(tId);
        if (t==null) { System.out.println("Not found: "+tId); input.pause(); return; }
        System.out.printf("\n%-16s: %s%n","Transaction ID",t.getTransactionId());
        System.out.printf("%-16s: %s%n","Customer ID",t.getCustomerId());
        System.out.printf("%-16s: %s%n","Product ID",t.getProductId());
        System.out.printf("%-16s: %d%n","Quantity",t.getQuantity());
        System.out.printf("%-16s: $%.2f%n","Amount",t.getAmount());
        System.out.printf("%-16s: %d%n","Timestamp",t.getTimestamp());
        Product p = findP(t.getProductId());
        if (p!=null) System.out.printf("%-16s: %s%n","Product Name",p.getName());
        input.pause();
    }
    private void spendSummary() {
        String cId = input.readString("Customer ID (e.g. C0001): ");
        Customer c = findC(cId);
        if (c==null) { System.out.println("Not found: "+cId); input.pause(); return; }
        int count=0; double total=0;
        for (Transaction t : ds.getTransactions()) if (t.getCustomerId().equals(cId)) { count++; total+=t.getAmount(); }
        System.out.println("\nCustomer         : "+c.getName());
        System.out.printf("Total Purchases  : %d%n",count);
        System.out.printf("Total Spending   : $%.2f%n",total);
        if (count>0) System.out.printf("Avg per Purchase : $%.2f%n",total/count);
        input.pause();
    }
    private Customer    findC(String id) { for (Customer c    : ds.getCustomers())    if (c.getCustomerId().equals(id))    return c; return null; }
    private Transaction findT(String id) { for (Transaction t : ds.getTransactions()) if (t.getTransactionId().equals(id)) return t; return null; }
    private Product     findP(String id) { for (Product p     : ds.getProducts())     if (p.getProductId().equals(id))     return p; return null; }
}