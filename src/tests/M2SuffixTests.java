package tests;

import m2_suffix.*;
import core.DataLoader;
import core.RetailDataStore;
import m1_string.KMPMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class M2SuffixTests {

    public static void main(String[] args) {
        System.out.println("Running M2 Suffix Structure Tests...\n");
        
        boolean allPassed = true;
        allPassed &= testNormalization();
        allPassed &= testSuffixArray();
        allPassed &= testKasaiLCP();
        allPassed &= testSAIS();
        allPassed &= testAutomaton();
        allPassed &= testTree();
        allPassed &= testSimilarity();
        allPassed &= testCrossCheckM1();
        allPassed &= testRetailIntegration();
        
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static boolean testNormalization() {
        try {
            String in = "Premium   Wireless, Bluetooth Headphones.!!!";
            String out = TextNormalizer.normalize(in);
            if (!out.equals("premium wireless bluetooth headphones")) {
                throw new RuntimeException("Normalization failed: " + out);
            }
            System.out.println("[PASS] TextNormalizer");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] TextNormalizer: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSuffixArray() {
        try {
            SuffixArray sa = new SuffixArray("banana");
            int[] arr = sa.getSuffixArray();
            if (!Arrays.equals(arr, new int[]{5,3,1,0,4,2})) {
                throw new RuntimeException("SA construction failed: " + Arrays.toString(arr));
            }
            
            List<Integer> r1 = sa.search("ana");
            Collections.sort(r1); // sorting just for asserting equality
            if (!r1.equals(Arrays.asList(1,3))) {
                throw new RuntimeException("SA search failed for 'ana': " + r1);
            }
            
            System.out.println("[PASS] SuffixArray");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SuffixArray: " + e.getMessage());
            return false;
        }
    }

    private static boolean testKasaiLCP() {
        try {
            int[] sa = new int[]{5,3,1,0,4,2}; // banana
            int[] lcp = KasaiLCP.buildLCPArray("banana", sa);
            if (!Arrays.equals(lcp, new int[]{0,1,3,0,0,2})) {
                throw new RuntimeException("LCP failed: " + Arrays.toString(lcp));
            }
            System.out.println("[PASS] KasaiLCP");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] KasaiLCP: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSAIS() {
        try {
            String[] tests = {"banana", "mississippi", "abracadabra", "aaaaa", "abcabcabc"};
            for (String t : tests) {
                SuffixArray sa1 = new SuffixArray(t);
                int[] sa2 = SAIS.build(t);
                if (!Arrays.equals(sa1.getSuffixArray(), sa2)) {
                    throw new RuntimeException("SAIS mismatch on " + t);
                }
            }
            System.out.println("[PASS] SA-IS");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SA-IS: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAutomaton() {
        try {
            SuffixAutomaton sam = new SuffixAutomaton("banana");
            if (!sam.contains("ana")) throw new RuntimeException("SAM failed contains ana");
            if (!sam.contains("nan")) throw new RuntimeException("SAM failed contains nan");
            if (sam.contains("apple")) throw new RuntimeException("SAM failed contains apple");
            System.out.println("[PASS] SuffixAutomaton");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SuffixAutomaton: " + e.getMessage());
            return false;
        }
    }

    private static boolean testTree() {
        try {
            SuffixTree st = new SuffixTree("banana");
            if (!st.contains("ana")) throw new RuntimeException("ST failed contains ana");
            if (!st.contains("ban")) throw new RuntimeException("ST failed contains ban");
            if (st.contains("apple")) throw new RuntimeException("ST failed contains apple");
            System.out.println("[PASS] SuffixTree");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] SuffixTree: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSimilarity() {
        try {
            String A = "wireless bluetooth noise cancelling headphones";
            String B = "premium wireless bluetooth headphones";
            SimilarityResult sr = DocumentSimilarityEngine.compare(A, B);
            if (!sr.getLongestCommonSubstring().contains("wireless bluetooth")) {
                throw new RuntimeException("Similarity LCS failed: " + sr.getLongestCommonSubstring());
            }
            System.out.println("[PASS] DocumentSimilarityEngine");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] DocumentSimilarityEngine: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCrossCheckM1() {
        try {
            String text = "aaaaa";
            String pattern = "aaa";
            List<Integer> resKMP = KMPMatcher.search(text, pattern);
            
            SuffixArray sa = new SuffixArray(text);
            List<Integer> resSA = sa.search(pattern);
            
            Collections.sort(resSA); // Normalize order
            
            if (!resKMP.equals(resSA)) {
                throw new RuntimeException("M1 KMP != M2 SA. KMP: " + resKMP + " SA: " + resSA);
            }
            System.out.println("[PASS] M1 / M2 Cross-Check");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Cross-Check: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRetailIntegration() {
        try {
            DataLoader loader = new DataLoader("data");
            RetailDataStore ds = loader.loadAll();
            M2DocumentEngine engine = new M2DocumentEngine(ds);
            
            List<String> matches = engine.searchReviewSubstring("P0001", "battery");
            // Not asserting empty since it depends on actual data. Just verifying it runs.
            if (matches == null) throw new RuntimeException("Review engine returned null");
            
            SimilarityResult sr = engine.compareProductDescriptions("P0001", "P0002");
            if (sr == null) throw new RuntimeException("Similarity engine returned null");
            
            System.out.println("[PASS] RetailIQ M2 Integration");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Retail Integration: " + e.getMessage());
            return false;
        }
    }
}
