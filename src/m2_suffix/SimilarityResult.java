package m2_suffix;

import java.util.List;

public class SimilarityResult {
    private final String documentA;
    private final String documentB;
    private final String longestCommonSubstring;
    private final List<String> commonPhrases;
    private final double similarityScore;

    public SimilarityResult(String documentA, String documentB, String longestCommonSubstring, List<String> commonPhrases, double similarityScore) {
        this.documentA = documentA;
        this.documentB = documentB;
        this.longestCommonSubstring = longestCommonSubstring;
        this.commonPhrases = commonPhrases;
        this.similarityScore = similarityScore;
    }

    public String getDocumentA() { return documentA; }
    public String getDocumentB() { return documentB; }
    public String getLongestCommonSubstring() { return longestCommonSubstring; }
    public List<String> getCommonPhrases() { return commonPhrases; }
    public double getSimilarityScore() { return similarityScore; }
    
    @Override
    public String toString() {
        return "LCS: '" + longestCommonSubstring + "', Phrases: " + commonPhrases.size() + ", Similarity: " + String.format("%.2f", similarityScore);
    }
}
