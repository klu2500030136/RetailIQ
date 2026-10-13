package m4_flow;

public class Dinic {
    public static MaxFlowResult maxFlow(FlowNetwork network, int source, int sink) {
        int total = 0;
        int phases = 0;
        int[] level = new int[network.size()];
        int[] next = new int[network.size()];

        while (buildLevels(network, source, sink, level)) {
            for (int i = 0; i < next.length; i++) next[i] = 0;
            int pushed;
            while ((pushed = send(network, source, sink, Integer.MAX_VALUE, level, next)) > 0) {
                total += pushed;
            }
            phases++;
        }
        return new MaxFlowResult(total, phases, new boolean[network.size()]);
    }

    private static boolean buildLevels(FlowNetwork network, int source, int sink, int[] level) {
        for (int i = 0; i < level.length; i++) level[i] = -1;
        int[] queue = new int[network.size()];
        int head = 0;
        int tail = 0;
        level[source] = 0;
        queue[tail++] = source;
        while (head < tail) {
            int u = queue[head++];
            for (FlowNetwork.Edge edge : network.edgesFrom(u)) {
                if (level[edge.to] < 0 && edge.residualCapacity() > 0) {
                    level[edge.to] = level[u] + 1;
                    if (edge.to == sink) return true;
                    queue[tail++] = edge.to;
                }
            }
        }
        return level[sink] >= 0;
    }

    private static int send(FlowNetwork network, int u, int sink, int pushed, int[] level, int[] next) {
        if (u == sink) return pushed;
        for (; next[u] < network.edgesFrom(u).size(); next[u]++) {
            FlowNetwork.Edge edge = network.edgesFrom(u).get(next[u]);
            if (edge.residualCapacity() > 0 && level[edge.to] == level[u] + 1) {
                int flow = send(network, edge.to, sink, Math.min(pushed, edge.residualCapacity()), level, next);
                if (flow > 0) {
                    edge.addFlow(flow);
                    return flow;
                }
            }
        }
        return 0;
    }
}
