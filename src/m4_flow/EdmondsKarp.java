package m4_flow;

import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {
    public static MaxFlowResult maxFlow(FlowNetwork network, int source, int sink) {
        int totalFlow = 0;
        int pathCount = 0;
        FlowNetwork.Edge[] parent = new FlowNetwork.Edge[network.size()];

        while (bfs(network, source, sink, parent)) {
            int bottleneck = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v].from) {
                bottleneck = Math.min(bottleneck, parent[v].residualCapacity());
            }
            for (int v = sink; v != source; v = parent[v].from) {
                parent[v].addFlow(bottleneck);
            }
            totalFlow += bottleneck;
            pathCount++;
        }
        return new MaxFlowResult(totalFlow, pathCount, reachable(network, source));
    }

    private static boolean bfs(FlowNetwork network, int source, int sink, FlowNetwork.Edge[] parent) {
        for (int i = 0; i < parent.length; i++) parent[i] = null;
        boolean[] visited = new boolean[network.size()];
        Queue<Integer> queue = new LinkedList<>();
        visited[source] = true;
        queue.add(source);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (FlowNetwork.Edge edge : network.edgesFrom(u)) {
                if (!visited[edge.to] && edge.residualCapacity() > 0) {
                    visited[edge.to] = true;
                    parent[edge.to] = edge;
                    if (edge.to == sink) return true;
                    queue.add(edge.to);
                }
            }
        }
        return false;
    }

    private static boolean[] reachable(FlowNetwork network, int source) {
        boolean[] visited = new boolean[network.size()];
        Queue<Integer> queue = new LinkedList<>();
        visited[source] = true;
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (FlowNetwork.Edge edge : network.edgesFrom(u)) {
                if (!visited[edge.to] && edge.residualCapacity() > 0) {
                    visited[edge.to] = true;
                    queue.add(edge.to);
                }
            }
        }
        return visited;
    }
}
