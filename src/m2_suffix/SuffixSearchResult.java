package m2_suffix;

import java.util.List;

public class SuffixSearchResult {
    private final String query;
    private final List<Integer> matchingPositions;
    private final int matchCount;

    public SuffixSearchResult(String query, List<Integer> matchingPositions) {
        this.query = query;
        this.matchingPositions = matchingPositions;
        this.matchCount = matchingPositions != null ? matchingPositions.size() : 0;
    }

    public String getQuery() { return query; }
    public List<Integer> getMatchingPositions() { return matchingPositions; }
    public int getMatchCount() { return matchCount; }
}
