package cli;

import core.RetailDataStore;
import m1_string.StringSearchEngine;
import m2_suffix.M2DocumentEngine;

public class RetailCLI {
    private final RetailDataStore dataStore;
    private final StringSearchEngine m1Engine;
    private final M2DocumentEngine m2Engine;
    private final InputHelper input;

    public RetailCLI(RetailDataStore dataStore) {
        this.dataStore = dataStore;
        this.m1Engine  = new StringSearchEngine(dataStore);
        this.m2Engine  = new M2DocumentEngine(dataStore);
        this.input     = new InputHelper();
    }

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                    RETAILIQ MENU");
            System.out.println("============================================================");
            System.out.println("1.  Retail Data Explorer");
            System.out.println("2.  Product Search");
            System.out.println("3.  Customer & Transaction Analysis");
            System.out.println("4.  Review Search & Analysis");
            System.out.println("5.  Product Description Similarity");
            System.out.println("6.  String Algorithm Lab");
            System.out.println("7.  Suffix Structure Lab");
            System.out.println("8.  Advanced DP Analytics (M3)");
            System.out.println("9.  Algorithm Benchmarks");
            System.out.println("10. Project / Module Status");
            System.out.println();
            System.out.println("11. M4 Network Flow");
            System.out.println("12. M5 NP-Completeness");
            System.out.println("13. M6 Randomized & Parallel");
            System.out.println();
            System.out.println("0.  Exit");
            System.out.println("============================================================");
            int choice = input.readInt("Enter your choice: ", 0, 13);
            switch (choice) {
                case 1:  new DataExplorerMenu(input, dataStore).show();                break;
                case 2:  new ProductSearchMenu(input, m1Engine, dataStore).show();     break;
                case 3:  new CustomerAnalysisMenu(input, dataStore).show();            break;
                case 4:  new ReviewAnalysisMenu(input, m1Engine, m2Engine).show();     break;
                case 5:  new ProductSimilarityMenu(input, dataStore, m2Engine).show(); break;
                case 6:  new StringLabMenu(input).show();                              break;
                case 7:  new SuffixLabMenu(input).show();                              break;
                case 8:  new M3Menu(input, dataStore).show();                          break;
                case 9:  new BenchmarkMenu(input).show();                              break;
                case 10: new StatusMenu(input, dataStore).show();                      break;
                case 11: new M4Menu(input, dataStore).show();                          break;
                case 12: new M5Menu(input, dataStore).show();                          break;
                case 13: new M6Menu(input, dataStore).show();                          break;
                case 0:
                    System.out.println("\nExiting RetailIQ. Goodbye!\n");
                    return;
            }
        }
    }
}
