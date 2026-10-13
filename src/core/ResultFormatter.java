package core;

import model.*;
import java.util.List;

public class ResultFormatter {
    
    public static void printHeader(String title) {
        System.out.println("----------------------------------------");
        System.out.println(title);
        System.out.println("----------------------------------------");
    }
    
    public static void printItem(String label, Object value) {
        System.out.printf("%-15s: %s\n", label, value != null ? value.toString() : "null");
    }

    public static void printResult(Object result) {
        if (result == null) {
            System.out.println("Result: Not Found (null)");
            return;
        }

        if (result instanceof String) {
            System.out.println("Result:\n" + result);
        } else if (result instanceof Product) {
            Product p = (Product) result;
            System.out.println("Result:");
            printItem("ID", p.getProductId());
            printItem("Name", p.getName());
            printItem("Category", p.getCategory());
            printItem("Price", p.getPrice());
            printItem("Rating", p.getRating());
            printItem("Stock", p.getStock());
            printItem("Warehouse", p.getWarehouseId());
            printItem("Sales", p.getSalesCount());
        } else if (result instanceof Customer) {
            Customer c = (Customer) result;
            System.out.println("Result:");
            printItem("ID", c.getCustomerId());
            printItem("Name", c.getName());
        } else if (result instanceof List) {
            List<?> list = (List<?>) result;
            System.out.println("Results (" + list.size() + " items):");
            for (Object item : list) {
                if (item instanceof Review) {
                    Review r = (Review) item;
                    System.out.println("- Review " + r.getReviewId() + " (Rating: " + r.getRating() + ") by " + r.getCustomerId() + ": " + r.getReviewText());
                } else if (item instanceof Transaction) {
                    Transaction t = (Transaction) item;
                    System.out.println("- Transaction " + t.getTransactionId() + " (Amount: $" + t.getAmount() + ") by " + t.getCustomerId() + " for " + t.getProductId());
                } else {
                    System.out.println("- " + item.toString());
                }
            }
        } else {
            System.out.println("Result:\n" + result.toString());
        }
    }
}

