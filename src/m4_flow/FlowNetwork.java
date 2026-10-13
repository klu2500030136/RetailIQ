package m4_flow;

import java.util.ArrayList;
import java.util.List;

public class FlowNetwork {
    public static class Edge {
        public final int from;
        public final int to;
        public final int capacity;
        public int flow;
        private Edge reverse;

        private Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public int residualCapacity() {
            return capacity - flow;
        }

        public void addFlow(int amount) {
            flow += amount;
            reverse.flow -= amount;
        }
    }

    private final List<Edge>[] graph;

    @SuppressWarnings("unchecked")
    public FlowNetwork(int vertices) {
        graph = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to, int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        Edge forward = new Edge(from, to, capacity);
        Edge backward = new Edge(to, from, 0);
        forward.reverse = backward;
        backward.reverse = forward;
        graph[from].add(forward);
        graph[to].add(backward);
    }

    public List<Edge> edgesFrom(int vertex) {
        return graph[vertex];
    }

    public int size() {
        return graph.length;
    }
}
