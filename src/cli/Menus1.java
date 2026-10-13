package cli;

import core.RetailDataStore;
import m1_string.*;
import m2_suffix.*;
import model.*;
import java.util.ArrayList;
import java.util.List;

class DataExplorerMenu {
    private final InputHelper input;
    private final RetailDataStore ds;
    DataExplorerMenu(InputHelper input, RetailDataStore ds) { this.input=input; this.ds=ds; }
    public void show() {
        while (true) {
            System.out.println("\n========== RETAIL DATA EXPLORER ==========");
            System.out.println("1. View Product by ID");
            System.out.println("2. List Products");
            System.out.println("3. View Customer by ID");
            System.out.println("4. View Transaction by ID");
            System.out.println("5. View Review by ID");
            System.out.println("6. Dataset Statistics");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 6);
            if (c == 0) return;
            switch (c) {
                case 1: viewProductById();     break;
                case 2: listProducts();        break;
                case 3: viewCustomerById();    break;
                case 4: viewTransactionById(); break;
                case 5: viewReviewById();      break;
                case 6: showStats();           break;
            }
        }
    }
    private void viewProductById() {
        String id = input.readString("Enter Product ID (e.g. P0001): ");
        Product p = findProduct(id);
        if (p == null) { System.out.println("Product not found: " + id); }
        else {
            System.out.printf("\n%-14s: %s%n","ID",p.getProductId());
            System.out.printf("%-14s: %s%n","Name",p.getName());
            System.out.printf("%-14s: %s%n","Category",p.getCategory());
            System.out.printf("%-14s: $%.2f%n","Price",p.getPrice());
            System.out.printf("%-14s: %.1f%n","Rating",p.getRating());
            System.out.printf("%-14s: %d%n","Stock",p.getStock());
            System.out.printf("%-14s: %s%n","Warehouse",p.getWarehouseId());
            System.out.printf("%-14s: %d%n","Sales Count",p.getSalesCount());
            System.out.printf("%-14s: %s%n","Description",p.getDescription());
        }
        input.pause();
    }
    private void listProducts() {
        int n = input.readInt("How many products to display? (1-150): ", 1, 150);
        System.out.printf("\n%-8s %-36s %-15s %s%n","ID","Name","Category","Price");
        System.out.println("-".repeat(72));
        List<Product> prods = ds.getProducts();
        for (int i = 0; i < Math.min(n, prods.size()); i++) {
            Product p = prods.get(i);
            System.out.printf("%-8s %-36s %-15s $%.2f%n",p.getProductId(),p.getName(),p.getCategory(),p.getPrice());
        }
        input.pause();
    }
    private void viewCustomerById() {
        String id = input.readString("Enter Customer ID (e.g. C0001): ");
        Customer c = findCustomer(id);
        if (c == null) { System.out.println("Customer not found: " + id); }
        else {
            System.out.printf("\n%-14s: %s%n","Customer ID",c.getCustomerId());
            System.out.printf("%-14s: %s%n","Name",c.getName());
        }
        input.pause();
    }
    private void viewTransactionById() {
        String id = input.readString("Enter Transaction ID (e.g. T00001): ");
        Transaction t = findTransaction(id);
        if (t == null) { System.out.println("Transaction not found: " + id); }
        else {
            System.out.printf("\n%-16s: %s%n","Transaction ID",t.getTransactionId());
            System.out.printf("%-16s: %s%n","Customer ID",t.getCustomerId());
            System.out.printf("%-16s: %s%n","Product ID",t.getProductId());
            System.out.printf("%-16s: %d%n","Quantity",t.getQuantity());
            System.out.printf("%-16s: $%.2f%n","Amount",t.getAmount());
            System.out.printf("%-16s: %d%n","Timestamp",t.getTimestamp());
        }
        input.pause();
    }
    private void viewReviewById() {
        String id = input.readString("Enter Review ID (e.g. R0001): ");
        Review r = findReview(id);
        if (r == null) { System.out.println("Review not found: " + id); }
        else {
            System.out.printf("\n%-14s: %s%n","Review ID",r.getReviewId());
            System.out.printf("%-14s: %s%n","Product ID",r.getProductId());
            System.out.printf("%-14s: %s%n","Customer ID",r.getCustomerId());
            System.out.printf("%-14s: %.1f%n","Rating",r.getRating());
            System.out.printf("%-14s: %s%n","Text",r.getReviewText());
        }
        input.pause();
    }
    private void showStats() {
        System.out.printf("\n%-16s: %d%n","Products",ds.getProducts().size());
        System.out.printf("%-16s: %d%n","Customers",ds.getCustomers().size());
        System.out.printf("%-16s: %d%n","Transactions",ds.getTransactions().size());
        System.out.printf("%-16s: %d%n","Reviews",ds.getReviews().size());
        System.out.printf("%-16s: %d%n","Warehouses",ds.getWarehouses().size());
        System.out.printf("%-16s: %d%n","Stores",ds.getStores().size());
        System.out.printf("%-16s: %d%n","Inventory",ds.getInventories().size());
        System.out.printf("%-16s: %d%n","Activities",ds.getActivities().size());
        input.pause();
    }
    private Product     findProduct(String id)     { for (Product p     : ds.getProducts())     if (p.getProductId().equals(id))     return p; return null; }
    private Customer    findCustomer(String id)    { for (Customer c    : ds.getCustomers())    if (c.getCustomerId().equals(id))    return c; return null; }
    private Transaction findTransaction(String id) { for (Transaction t : ds.getTransactions()) if (t.getTransactionId().equals(id)) return t; return null; }
    private Review      findReview(String id)      { for (Review r      : ds.getReviews())      if (r.getReviewId().equals(id))      return r; return null; }
}

class ProductSearchMenu {
    private final InputHelper input;
    private final StringSearchEngine m1Engine;
    private final RetailDataStore ds;
    ProductSearchMenu(InputHelper input, StringSearchEngine m1Engine, RetailDataStore ds) { this.input=input; this.m1Engine=m1Engine; this.ds=ds; }
    public void show() {
        while (true) {
            System.out.println("\n========== PRODUCT SEARCH ==========");
            System.out.println("1. Naive");
            System.out.println("2. KMP");
            System.out.println("3. Rabin-Karp");
            System.out.println("4. Compare Naive vs KMP");
            System.out.println("5. Fuzzy Search (M3 DP)");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 5);
            if (c == 0) return;
            String query = input.readString("Enter search query: ");
            if (query.isEmpty()) { System.out.println("Query cannot be empty."); continue; }
            switch (c) {
                case 1: printResults("Naive",      m1Engine.searchProductsNaive(query));         break;
                case 2: printResults("KMP",        m1Engine.searchProductsKMP(query));           break;
                case 3: printResults("Rabin-Karp", m1Engine.searchProductCodesRabinKarp(query)); break;
                case 4: compareNaiveKMP(query); break;
                case 5: fuzzySearch(query); break;
            }
        }
    }
    private void printResults(String algo, List<Product> results) {
        System.out.println("\n--- " + algo + " --- Found: " + results.size() + " product(s)");
        System.out.println("-".repeat(60));
        for (int i = 0; i < Math.min(10, results.size()); i++) {
            Product p = results.get(i);
            System.out.printf("%2d. %-8s %-36s $%.2f [%s]%n",i+1,p.getProductId(),p.getName(),p.getPrice(),p.getCategory());
        }
        if (results.size() > 10) System.out.println("    ... and " + (results.size()-10) + " more.");
        if (results.isEmpty()) System.out.println("No exact matches. Try fuzzy search (option 5).");
        input.pause();
    }
    private void compareNaiveKMP(String query) {
        long t1 = System.nanoTime(); List<Product> naive = m1Engine.searchProductsNaive(query);
        long t2 = System.nanoTime(); List<Product> kmp   = m1Engine.searchProductsKMP(query);
        long t3 = System.nanoTime();
        System.out.printf("%nNaive : %d match(es)  %.3f ms%n", naive.size(), (t2-t1)/1e6);
        System.out.printf("KMP   : %d match(es)  %.3f ms%n",   kmp.size(),   (t3-t2)/1e6);
        input.pause();
    }
    private void fuzzySearch(String query) {
        int threshold = input.readInt("Distance threshold (1-5): ", 1, 5);
        m3_dp.FuzzySearchEngine fse = new m3_dp.FuzzySearchEngine(ds);
        List<m3_dp.FuzzyMatchResult> res = fse.searchProducts(query, m3_dp.FuzzySearchEngine.Algorithm.DAMERAU_LEVENSHTEIN, threshold);
        System.out.println("\n--- Fuzzy (Damerau-Levenshtein) --- threshold=" + threshold);
        if (res.isEmpty()) System.out.println("No close matches found.");
        else for (int i = 0; i < Math.min(5, res.size()); i++)
            System.out.printf("%2d. %-8s %-36s dist=%d%n",i+1,res.get(i).product.getProductId(),res.get(i).matchedName,res.get(i).distance);
        input.pause();
    }
}

class ReviewAnalysisMenu {
    private final InputHelper input;
    private final StringSearchEngine m1Engine;
    private final M2DocumentEngine m2Engine;
    ReviewAnalysisMenu(InputHelper input, StringSearchEngine m1Engine, M2DocumentEngine m2Engine) { this.input=input; this.m1Engine=m1Engine; this.m2Engine=m2Engine; }
    public void show() {
        while (true) {
            System.out.println("\n========== REVIEW SEARCH & ANALYSIS ==========");
            System.out.println("1. Global phrase search  (Suffix Array)");
            System.out.println("2. Per-product phrase search (Z-Function)");
            System.out.println("3. Multi-keyword detection (Aho-Corasick)");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 3);
            if (c == 0) return;
            if (c == 1) {
                String phrase = input.readString("Enter phrase: ");
                if (phrase.isEmpty()) { System.out.println("Phrase cannot be empty."); continue; }
                long t1 = System.nanoTime();
                List<String> m = m2Engine.globalReviewCorpusSearch(phrase);
                long t2 = System.nanoTime();
                System.out.println("Matching reviews: " + m.size());
                for (int i = 0; i < Math.min(5, m.size()); i++) System.out.println("  " + m.get(i));
                if (m.size()>5) System.out.println("  ... and "+(m.size()-5)+" more.");
                System.out.printf("Time: %.3f ms%n",(t2-t1)/1e6);
                input.pause();
            } else if (c == 2) {
                String pid    = input.readString("Product ID (ENTER=all): ");
                String phrase = input.readString("Phrase: ");
                if (phrase.isEmpty()) { System.out.println("Phrase cannot be empty."); continue; }
                long t1 = System.nanoTime();
                List<String> m = m1Engine.findReviewPhraseZ(pid.isEmpty()?null:pid, phrase);
                long t2 = System.nanoTime();
                System.out.println("Occurrences: " + m.size());
                for (int i = 0; i < Math.min(5, m.size()); i++) System.out.println("  " + m.get(i));
                System.out.printf("Time: %.3f ms%n",(t2-t1)/1e6);
                input.pause();
            } else {
                String kws = input.readString("Keywords (comma separated): ");
                if (kws.trim().isEmpty()) { System.out.println("Keywords cannot be empty."); continue; }
                List<String> kwList = new ArrayList<>();
                for (String k : kws.split(",")) if (!k.trim().isEmpty()) kwList.add(k.trim());
                long t1 = System.nanoTime();
                List<String> res = m1Engine.findReviewKeywordsAhoCorasick(null, kwList);
                long t2 = System.nanoTime();
                System.out.println("Keyword matches: " + res.size());
                for (int i = 0; i < Math.min(8, res.size()); i++) System.out.println("  " + res.get(i));
                if (res.size()>8) System.out.println("  ... and "+(res.size()-8)+" more.");
                System.out.printf("Time: %.3f ms%n",(t2-t1)/1e6);
                input.pause();
            }
        }
    }
}

class ProductSimilarityMenu {
    private final InputHelper input;
    private final RetailDataStore ds;
    private final M2DocumentEngine m2Engine;
    ProductSimilarityMenu(InputHelper input, RetailDataStore ds, M2DocumentEngine m2Engine) { this.input=input; this.ds=ds; this.m2Engine=m2Engine; }
    public void show() {
        while (true) {
            System.out.println("\n========== PRODUCT DESCRIPTION SIMILARITY ==========");
            System.out.println("1. Suffix-based LCS similarity");
            System.out.println("2. Needleman-Wunsch (global alignment)");
            System.out.println("3. Smith-Waterman  (local alignment)");
            System.out.println("4. Compare all methods");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 4);
            if (c == 0) return;
            String idA = input.readString("Product A ID: ");
            String idB = input.readString("Product B ID: ");
            if (c==1||c==4) suffixSim(idA,idB);
            if (c==2||c==4) nwAlign(idA,idB);
            if (c==3||c==4) swAlign(idA,idB);
            input.pause();
        }
    }
    private void suffixSim(String a, String b) {
        SimilarityResult sr = m2Engine.compareProductDescriptions(a, b);
        if (sr==null) { System.out.println("Products not found."); return; }
        System.out.println("\n--- Suffix LCS ---");
        System.out.println("Longest Common Substring: \"" + sr.getLongestCommonSubstring() + "\"");
        List<String> phrases = cleanPhrases(sr.getCommonPhrases());
        System.out.println("Meaningful Phrases (" + phrases.size() + "):");
        if (phrases.isEmpty()) System.out.println("  (none meeting word-boundary/length criteria)");
        for (String cp : phrases) System.out.println("  \"" + cp + "\"");
    }
    private void nwAlign(String a, String b) {
        m3_dp.ProductAlignmentEngine pae = new m3_dp.ProductAlignmentEngine(ds);
        m3_dp.AlignmentResult r = pae.alignProductDescriptions(a,b,m3_dp.ProductAlignmentEngine.AlignmentType.GLOBAL_NEEDLEMAN_WUNSCH);
        if (r==null) { System.out.println("NW: products not found."); return; }
        System.out.println("\n--- Needleman-Wunsch (Global) --- Score: " + r.score);
        int lim=80;
        System.out.println("A: "+(r.alignedA.length()>lim?r.alignedA.substring(0,lim)+"...":r.alignedA));
        System.out.println("B: "+(r.alignedB.length()>lim?r.alignedB.substring(0,lim)+"...":r.alignedB));
    }
    private void swAlign(String a, String b) {
        m3_dp.ProductAlignmentEngine pae = new m3_dp.ProductAlignmentEngine(ds);
        m3_dp.AlignmentResult r = pae.alignProductDescriptions(a,b,m3_dp.ProductAlignmentEngine.AlignmentType.LOCAL_SMITH_WATERMAN);
        if (r==null) { System.out.println("SW: products not found."); return; }
        System.out.println("\n--- Smith-Waterman (Local) --- Score: " + r.score);
        System.out.println("Region A: ["+r.startA+", "+r.endA+"]  Region B: ["+r.startB+", "+r.endB+"]");
        int lim=80;
        System.out.println("A: "+(r.alignedA.length()>lim?r.alignedA.substring(0,lim)+"...":r.alignedA));
        System.out.println("B: "+(r.alignedB.length()>lim?r.alignedB.substring(0,lim)+"...":r.alignedB));
    }
    private List<String> cleanPhrases(List<String> raw) {
        if (raw==null) return new ArrayList<>();
        List<String> clean = new ArrayList<>();
        for (String s : raw) {
            if (s==null) continue;
            String t = s.trim();
            if (t.length()<5) continue;
            if (!Character.isLetterOrDigit(t.charAt(0))) continue;
            if (!Character.isLetterOrDigit(t.charAt(t.length()-1))) continue;
            clean.add(t);
        }
        List<String> result = new ArrayList<>();
        for (String candidate : clean) {
            boolean isSub = false;
            for (String other : clean) if (!candidate.equals(other) && other.contains(candidate)) { isSub=true; break; }
            if (!isSub && !result.contains(candidate)) result.add(candidate);
        }
        return result;
    }
}