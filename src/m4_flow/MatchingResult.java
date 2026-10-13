package m4_flow;

import java.util.List;

public class MatchingResult {
    public final int cardinality;
    public final List<String> pairs;

    public MatchingResult(int cardinality, List<String> pairs) {
        this.cardinality = cardinality;
        this.pairs = pairs;
    }
}
