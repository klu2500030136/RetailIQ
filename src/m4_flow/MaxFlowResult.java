package m4_flow;

public class MaxFlowResult {
    public final int maxFlow;
    public final int augmentingPathCount;
    public final boolean[] reachableFromSource;

    public MaxFlowResult(int maxFlow, int augmentingPathCount, boolean[] reachableFromSource) {
        this.maxFlow = maxFlow;
        this.augmentingPathCount = augmentingPathCount;
        this.reachableFromSource = reachableFromSource;
    }
}
