package cli;

import core.RetailDataStore;
import m1_string.*;
import m2_suffix.*;

import java.util.Arrays;
import java.util.List;

class StringLabMenu {
    private final InputHelper input;
    StringLabMenu(InputHelper input) { this.input=input; }
    public void show() {
        while (true) {
            System.out.println("\n========== STRING ALGORITHM LAB ==========");
            System.out.println("1. Naive Pattern Matching");
            System.out.println("2. KMP  (+LPS table)");
            System.out.println("3. Z-Function");
            System.out.println("4. Rabin-Karp");
            System.out.println("5. Aho-Corasick");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 5);
            if (c == 0) return;
            if (c <= 4) {
                String text    = input.readString("Enter text   : ");
                String pattern = input.readString("Enter pattern: ");
                if (text.isEmpty()||pattern.isEmpty()) { System.out.println("Cannot be empty."); continue; }
                switch (c) {
                    case 1: {
                        List<Integer> pos = NaiveMatcher.search(text, pattern);
                        System.out.println("Algorithm: Naive  O(nm)");
                        System.out.println("Matches at: " + pos + "  (" + pos.size() + " occurrence(s))");
                        break;
                    }
                    case 2: {
                        int[] lps = KMPMatcher.buildLPS(pattern);
                        List<Integer> pos = KMPMatcher.search(text, pattern);
                        System.out.println("Algorithm: KMP  O(n+m)");
                        System.out.println("LPS Array: " + Arrays.toString(lps));
                        System.out.println("Matches at: " + pos + "  (" + pos.size() + " occurrence(s))");
                        break;
                    }
                    case 3: {
                        int[] zArr = ZFunction.computeZ(pattern + "$" + text);
                        List<Integer> pos = ZFunction.search(text, pattern);
                        System.out.println("Algorithm: Z-Function  O(n+m)");
                        System.out.println("Z Array: " + Arrays.toString(zArr));
                        System.out.println("Matches at: " + pos + "  (" + pos.size() + " occurrence(s))");
                        break;
                    }
                    case 4: {
                        List<Integer> pos = RabinKarpMatcher.search(text, pattern);
                        System.out.println("Algorithm: Rabin-Karp  O(n+m) avg");
                        System.out.println("Matches at: " + pos + "  (" + pos.size() + " occurrence(s))");
                        break;
                    }
                }
            } else {
                String text = input.readString("Enter text to search in: ");
                int numKw   = input.readInt("Number of keywords: ", 1, 20);
                AhoCorasick ac = new AhoCorasick();
                for (int i = 0; i < numKw; i++) ac.addPattern(input.readString("  Keyword " + (i+1) + ": "));
                ac.buildFailureLinks();
                List<KeywordMatch> results = ac.search(text);
                System.out.println("Algorithm: Aho-Corasick  O(n + sum(m) + matches)");
                System.out.println("Hits: " + results.size());
                for (int i = 0; i < Math.min(10, results.size()); i++) System.out.println("  " + results.get(i));
            }
            input.pause();
        }
    }
}

class SuffixLabMenu {
    private final InputHelper input;
    SuffixLabMenu(InputHelper input) { this.input=input; }
    public void show() {
        while (true) {
            System.out.println("\n========== SUFFIX STRUCTURE LAB ==========");
            System.out.println("1. Suffix Array + Pattern Search");
            System.out.println("2. Kasai LCP Array");
            System.out.println("3. SA-IS vs Practical SA");
            System.out.println("4. Suffix Automaton");
            System.out.println("5. Suffix Tree");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 5);
            if (c == 0) return;
            String text = input.readString("Enter text: ");
            if (text.isEmpty()) { System.out.println("Cannot be empty."); continue; }
            switch (c) {
                case 1: {
                    String pat = input.readString("Enter pattern: ");
                    SuffixArray sa = new SuffixArray(text);
                    System.out.println("Suffix Array: " + Arrays.toString(sa.getSuffixArray()));
                    System.out.println("Matches: " + sa.search(pat));
                    break;
                }
                case 2: {
                    SuffixArray sa = new SuffixArray(text);
                    int[] lcp = KasaiLCP.buildLCPArray(text, sa.getSuffixArray());
                    System.out.println("Suffix Array: " + Arrays.toString(sa.getSuffixArray()));
                    System.out.println("LCP (Kasai): " + Arrays.toString(lcp));
                    break;
                }
                case 3: {
                    int[] p = new SuffixArray(text).getSuffixArray();
                    int[] s = SAIS.build(text);
                    System.out.println("Practical: " + Arrays.toString(p));
                    System.out.println("SA-IS    : " + Arrays.toString(s));
                    System.out.println("Consistent: " + (Arrays.equals(p,s) ? "PASS" : "FAIL"));
                    break;
                }
                case 4: {
                    String sub = input.readString("Enter substring: ");
                    SuffixAutomaton sam = new SuffixAutomaton(text);
                    System.out.println("Exists: " + (sam.contains(sub) ? "YES" : "NO"));
                    System.out.println("States: " + sam.getStateCount());
                    break;
                }
                case 5: {
                    String sub = input.readString("Enter substring: ");
                    SuffixTree st = new SuffixTree(text);
                    System.out.println("Exists: " + (st.contains(sub) ? "YES" : "NO"));
                    break;
                }
            }
            input.pause();
        }
    }
}

class BenchmarkMenu {
    private final InputHelper input;
    BenchmarkMenu(InputHelper input) { this.input=input; }
    public void show() {
        while (true) {
            System.out.println("\n========== ALGORITHM BENCHMARKS ==========");
            System.out.println("1. M1 String Algorithm Benchmarks");
            System.out.println("2. M2 Suffix Structure Benchmarks");
            System.out.println("3. M3 DP Benchmarks");
            System.out.println("4. M4 Network Flow Benchmarks");
            System.out.println("5. M5 NP/Approximation Benchmarks");
            System.out.println("6. M6 Randomized/Parallel Benchmarks");
            System.out.println("7. Run All");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 7);
            if (c == 0) return;
            if (c==1||c==7) { System.out.println(); m1_string.M1Demo.main(new String[0]); }
            if (c==2||c==7) { System.out.println(); m2_suffix.M2Demo.main(new String[0]); }
            if (c==3||c==7) { System.out.println(); m3_dp.M3Demo.main(new String[0]); }
            if (c==4||c==7) { System.out.println(); m4_flow.M4Demo.main(new String[0]); }
            if (c==5||c==7) { System.out.println(); m5_np.M5Demo.main(new String[0]); }
            if (c==6||c==7) { System.out.println(); m6_random_parallel.M6Demo.main(new String[0]); }
            input.pause();
        }
    }
}

class StatusMenu {
    private final InputHelper input;
    private final RetailDataStore ds;
    StatusMenu(InputHelper input, RetailDataStore ds) { this.input=input; this.ds=ds; }
    public void show() {
        System.out.println("\n========== RETAILIQ PROJECT STATUS ==========");
        System.out.println("PHASE 0 - Project Foundation           [COMPLETE]");
        System.out.println("PHASE 1 - Core/Data Engine             [COMPLETE]");
        System.out.println("PHASE 2 - M1 String Algorithms         [COMPLETE]");
        System.out.println("PHASE 3 - M2 Suffix Structures         [COMPLETE]");
        System.out.println("PHASE 4 - M3 Advanced DP               [COMPLETE]");
        System.out.println("PHASE 5 - M4 Network Flow              [COMPLETE]");
        System.out.println("PHASE 6 - M5 NP-Completeness           [COMPLETE]");
        System.out.println("PHASE 7 - M6 Randomized & Parallel     [COMPLETE]");
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
}
