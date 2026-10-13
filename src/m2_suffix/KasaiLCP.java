package m2_suffix;

public class KasaiLCP {
    
    /**
     * Builds the LCP (Longest Common Prefix) array in O(n) using Kasai's algorithm.
     * lcp[i] represents the longest common prefix between suffix sa[i] and sa[i-1].
     * lcp[0] is typically 0.
     */
    public static int[] buildLCPArray(String text, int[] sa) {
        int n = sa.length;
        int[] lcp = new int[n];
        int[] invSA = new int[n];
        
        // Compute inverse suffix array
        for (int i = 0; i < n; i++) {
            invSA[sa[i]] = i;
        }
        
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (invSA[i] > 0) {
                int j = sa[invSA[i] - 1]; // previous suffix in SA order
                while (i + k < n && j + k < n && text.charAt(i + k) == text.charAt(j + k)) {
                    k++;
                }
                lcp[invSA[i]] = k;
                if (k > 0) {
                    k--;
                }
            }
        }
        return lcp;
    }
}
