package tests;

import m1_string.*;
import core.DataLoader;
import core.RetailDataStore;
import model.Product;

import java.util.Arrays;
import java.util.List;

public class M1StringTests {

    public static void main(String[] args) {
        System.out.println("Running M1 String Algorithm Tests...\n");
        
        boolean allPassed = true;
        allPassed &= testNaive();
        allPassed &= testKMP();
        allPassed &= testZFunction();
        allPassed &= testRabinKarp();
        allPassed &= testAhoCorasick();
        allPassed &= testCrossAlgorithm();
        allPassed &= testRetailIntegration();
        
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static void assertEqual(List<Integer> actual, List<Integer> expected, String name) {
        if (!actual.equals(expected)) {
            System.out.println("[FAIL] " + name + " - Expected: " + expected + ", Got: " + actual);
            throw new RuntimeException("Test failed: " + name);
        }
    }

    private static boolean testNaive() {
        try {
            assertEqual(NaiveMatcher.search("aaaaa", "aaa"), Arrays.asList(0, 1, 2), "Naive multiple/overlapping");
            assertEqual(NaiveMatcher.search("hello", "x"), Arrays.asList(), "Naive no match");
            assertEqual(NaiveMatcher.search("text", ""), Arrays.asList(), "Naive empty pattern");
            assertEqual(NaiveMatcher.search("abc", "abcdef"), Arrays.asList(), "Naive pattern longer than text");
            System.out.println("[PASS] NaiveMatcher");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testKMP() {
        try {
            assertEqual(KMPMatcher.search("aaaaa", "aaa"), Arrays.asList(0, 1, 2), "KMP multiple/overlapping");
            assertEqual(KMPMatcher.search("hello", "x"), Arrays.asList(), "KMP no match");
            assertEqual(KMPMatcher.search("text", ""), Arrays.asList(), "KMP empty pattern");
            
            int[] lps = KMPMatcher.buildLPS("ABABCABAB");
            if (!Arrays.equals(lps, new int[]{0,0,1,2,0,1,2,3,4})) {
                System.out.println("[FAIL] KMP LPS - Got: " + Arrays.toString(lps));
                throw new RuntimeException("KMP LPS failed");
            }
            
            System.out.println("[PASS] KMPMatcher & LPS");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testZFunction() {
        try {
            int[] z1 = ZFunction.computeZ("aaaaa");
            if (!Arrays.equals(z1, new int[]{0,4,3,2,1})) {
                throw new RuntimeException("Z-array aaaaa failed");
            }
            
            int[] z2 = ZFunction.computeZ("abacaba");
            if (!Arrays.equals(z2, new int[]{0,0,1,0,3,0,1})) {
                throw new RuntimeException("Z-array abacaba failed");
            }
            
            assertEqual(ZFunction.search("aaaaa", "aaa"), Arrays.asList(0, 1, 2), "ZFunction search");
            
            System.out.println("[PASS] ZFunction");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] ZFunction: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRabinKarp() {
        try {
            assertEqual(RabinKarpMatcher.search("aaaaa", "aaa"), Arrays.asList(0, 1, 2), "RK multiple/overlapping");
            assertEqual(RabinKarpMatcher.search("hello", "x"), Arrays.asList(), "RK no match");
            assertEqual(RabinKarpMatcher.search("text", ""), Arrays.asList(), "RK empty pattern");
            assertEqual(RabinKarpMatcher.search("abc", "abcdef"), Arrays.asList(), "RK pattern longer than text");
            assertEqual(RabinKarpMatcher.search("ELEC-IPH15-256-BLK", "IPH15"), Arrays.asList(5), "RK product code search");
            
            System.out.println("[PASS] RabinKarpMatcher");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] RabinKarpMatcher: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAhoCorasick() {
        try {
            AhoCorasick ac = new AhoCorasick();
            ac.addPattern("he");
            ac.addPattern("she");
            ac.addPattern("his");
            ac.addPattern("hers");
            ac.buildFailureLinks();
            
            List<KeywordMatch> matches = ac.search("ushers");
            if (matches.size() != 3) {
                throw new RuntimeException("Expected 3 matches, got " + matches.size());
            }
            // ushers: she[1-3], he[2-3], hers[2-5]
            
            System.out.println("[PASS] AhoCorasick");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] AhoCorasick: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCrossAlgorithm() {
        try {
            String text = "aaaaabaaaaab";
            String pattern = "aaab";
            List<Integer> resNaive = NaiveMatcher.search(text, pattern);
            List<Integer> resKMP = KMPMatcher.search(text, pattern);
            List<Integer> resRK = RabinKarpMatcher.search(text, pattern);
            
            if (!resNaive.equals(resKMP) || !resNaive.equals(resRK)) {
                throw new RuntimeException("Cross-algorithm mismatch!");
            }
            
            System.out.println("[PASS] Cross-Algorithm Consistency");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Cross-Algorithm: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRetailIntegration() {
        try {
            DataLoader loader = new DataLoader("data");
            RetailDataStore ds = loader.loadAll();
            StringSearchEngine engine = new StringSearchEngine(ds);
            
            List<Product> kmpMatches = engine.searchProductsKMP("laptop");
            List<String> zMatches = engine.findReviewPhraseZ(null, "battery");
            List<Product> rkMatches = engine.searchProductCodesRabinKarp("P001");
            List<String> acMatches = engine.findReviewKeywordsAhoCorasick(null, Arrays.asList("wireless", "battery", "gaming"));
            
            if (kmpMatches == null || zMatches == null || rkMatches == null || acMatches == null) {
                throw new RuntimeException("Integration returned null");
            }
            
            System.out.println("[PASS] RetailIQ String Integration");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Retail Integration: " + e.getMessage());
            return false;
        }
    }
}
