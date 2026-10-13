package m1_string;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpMatcher {
    // Prime base and modulus
    private static final int BASE = 256;
    private static final int MODULUS = 101;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || pattern.length() > text.length()) {
            return matches;
        }

        int n = text.length();
        int m = pattern.length();
        
        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        // h = (BASE^(m-1)) % MODULUS
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MODULUS;
        }

        // Calculate hash value for pattern and first window of text
        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % MODULUS;
            textHash = (BASE * textHash + text.charAt(i)) % MODULUS;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                // Verification step (character by character)
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    matches.add(i);
                }
            }
            
            // Calculate hash value for next window
            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % MODULUS;
                if (textHash < 0) {
                    textHash = (textHash + MODULUS);
                }
            }
        }
        
        return matches;
    }
}
