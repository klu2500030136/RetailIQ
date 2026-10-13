package m4_flow;

import java.util.ArrayList;
import java.util.List;

public class BipartiteMatcher {
    public static MatchingResult maximumMatching(String[] left, String[] right, boolean[][] canMatch) {
        int n = left.length;
        int m = right.length;
        int source = 0;
        int leftStart = 1;
        int rightStart = leftStart + n;
        int sink = rightStart + m;
        FlowNetwork network = new FlowNetwork(sink + 1);

        for (int i = 0; i < n; i++) network.addEdge(source, leftStart + i, 1);
        for (int j = 0; j < m; j++) network.addEdge(rightStart + j, sink, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (canMatch[i][j]) network.addEdge(leftStart + i, rightStart + j, 1);
            }
        }

        MaxFlowResult flow = EdmondsKarp.maxFlow(network, source, sink);
        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (FlowNetwork.Edge edge : network.edgesFrom(leftStart + i)) {
                if (edge.to >= rightStart && edge.to < rightStart + m && edge.flow == 1) {
                    pairs.add(left[i] + " -> " + right[edge.to - rightStart]);
                }
            }
        }
        return new MatchingResult(flow.maxFlow, pairs);
    }
}
