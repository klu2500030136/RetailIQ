package m2_suffix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DocumentSimilarityEngine {

    public static SimilarityResult compare(String docA, String docB) {
        String normA = TextNormalizer.normalize(docA);
        String normB = TextNormalizer.normalize(docB);
        
        // Use a unique separator
        String combined = normA + "#" + normB;
        int boundary = normA.length();
        
        SuffixArray saBuilder = new SuffixArray(combined);
        int[] sa = saBuilder.getSuffixArray();
        int[] lcp = KasaiLCP.buildLCPArray(combined, sa);
        
        int n = sa.length;
        int maxLcp = 0;
        int maxLcpIndex = -1;
        
        Set<String> commonPhrases = new HashSet<>();
        int MIN_PHRASE_LENGTH = 5;
        
        for (int i = 1; i < n; i++) {
            // Check if adjacent suffixes in SA cross the document boundary
            boolean crossesBoundary = (sa[i - 1] < boundary && sa[i] > boundary) || 
                                      (sa[i - 1] > boundary && sa[i] < boundary);
            
            if (crossesBoundary) {
                if (lcp[i] > maxLcp) {
                    maxLcp = lcp[i];
                    maxLcpIndex = sa[i];
                }
                if (lcp[i] >= MIN_PHRASE_LENGTH) {
                    String phrase = combined.substring(sa[i], sa[i] + lcp[i]);
                    // Only add if it doesn't contain the separator
                    if (!phrase.contains("#")) {
                        commonPhrases.add(phrase);
                    }
                }
            }
        }
        
        String longestCommonSubstring = (maxLcp > 0 && maxLcpIndex != -1) ? 
                combined.substring(maxLcpIndex, maxLcpIndex + maxLcp) : "";
        
        if (longestCommonSubstring.contains("#")) {
            longestCommonSubstring = longestCommonSubstring.split("#")[0];
        }
        
        double similarityScore = 0;
        if (!normA.isEmpty() && !normB.isEmpty()) {
            similarityScore = (double) longestCommonSubstring.length() / Math.min(normA.length(), normB.length());
        }

        return new SimilarityResult(docA, docB, longestCommonSubstring, new ArrayList<>(commonPhrases), similarityScore);
    }
}
