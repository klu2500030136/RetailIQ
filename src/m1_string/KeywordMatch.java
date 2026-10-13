package m1_string;

public class KeywordMatch {
    private final String keyword;
    private final int startIndex;
    private final int endIndex;

    public KeywordMatch(String keyword, int startIndex, int endIndex) {
        this.keyword = keyword;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getKeyword() { return keyword; }
    public int getStartIndex() { return startIndex; }
    public int getEndIndex() { return endIndex; }

    @Override
    public String toString() {
        return keyword + " [" + startIndex + "-" + endIndex + "]";
    }
}
