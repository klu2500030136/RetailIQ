package m1_string;

import core.RetailDataStore;
import model.Product;
import model.Review;

import java.util.ArrayList;
import java.util.List;

public class StringSearchEngine {
    
    private final RetailDataStore dataStore;

    public StringSearchEngine(RetailDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Product> searchProductsNaive(String query) {
        List<Product> results = new ArrayList<>();
        if (query == null || query.isEmpty()) return results;
        
        String lowerQuery = query.toLowerCase();
        for (Product p : dataStore.getProducts()) {
            String combinedText = p.getName() + " " + p.getDescription();
            List<Integer> matches = NaiveMatcher.search(combinedText.toLowerCase(), lowerQuery);
            if (!matches.isEmpty()) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> searchProductsKMP(String query) {
        List<Product> results = new ArrayList<>();
        if (query == null || query.isEmpty()) return results;
        
        String lowerQuery = query.toLowerCase();
        for (Product p : dataStore.getProducts()) {
            String combinedText = p.getName() + " " + p.getDescription();
            List<Integer> matches = KMPMatcher.search(combinedText.toLowerCase(), lowerQuery);
            if (!matches.isEmpty()) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> searchProductCodesRabinKarp(String codePattern) {
        List<Product> results = new ArrayList<>();
        if (codePattern == null || codePattern.isEmpty()) return results;
        
        for (Product p : dataStore.getProducts()) {
            List<Integer> matches = RabinKarpMatcher.search(p.getProductId(), codePattern);
            if (!matches.isEmpty()) {
                results.add(p);
            }
        }
        return results;
    }

    public List<String> findReviewPhraseZ(String productId, String phrase) {
        List<String> matchingReviews = new ArrayList<>();
        if (phrase == null || phrase.isEmpty()) return matchingReviews;
        
        String lowerPhrase = phrase.toLowerCase();
        for (Review r : dataStore.getReviews()) {
            if (productId != null && !productId.equals(r.getProductId())) {
                continue;
            }
            List<Integer> matches = ZFunction.search(r.getReviewText().toLowerCase(), lowerPhrase);
            if (!matches.isEmpty()) {
                matchingReviews.add("Review " + r.getReviewId() + " at indices " + matches);
            }
        }
        return matchingReviews;
    }

    public List<String> findReviewKeywordsAhoCorasick(String reviewId, List<String> keywords) {
        List<String> results = new ArrayList<>();
        if (keywords == null || keywords.isEmpty()) return results;
        
        AhoCorasick ac = new AhoCorasick();
        for (String kw : keywords) {
            ac.addPattern(kw.toLowerCase());
        }
        ac.buildFailureLinks();

        for (Review r : dataStore.getReviews()) {
            if (reviewId != null && !reviewId.equals(r.getReviewId())) {
                continue;
            }
            List<KeywordMatch> matches = ac.search(r.getReviewText().toLowerCase());
            if (!matches.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Review ").append(r.getReviewId()).append(" matches: ");
                for (KeywordMatch m : matches) {
                    sb.append(m.getKeyword()).append(" ");
                }
                results.add(sb.toString().trim());
            }
        }
        return results;
    }
}
