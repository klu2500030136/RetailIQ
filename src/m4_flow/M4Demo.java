package m4_flow;

import core.DataLoader;
import core.RetailDataStore;

public class M4Demo {
    public static void main(String[] args) {
        FlowNetwork network = sampleNetwork();
        MaxFlowResult ek = EdmondsKarp.maxFlow(network, 0, 5);
        System.out.println("M4 Edmonds-Karp max flow: " + ek.maxFlow + " using " + ek.augmentingPathCount + " augmenting paths");

        FlowNetwork network2 = sampleNetwork();
        MaxFlowResult dinic = Dinic.maxFlow(network2, 0, 5);
        System.out.println("M4 Dinic max flow: " + dinic.maxFlow + " using " + dinic.augmentingPathCount + " level phases");

        String[] warehouses = {"W1", "W2", "W3"};
        String[] stores = {"S1", "S2", "S3"};
        boolean[][] edges = {
                {true, true, false},
                {false, true, true},
                {true, false, true}
        };
        MatchingResult matching = BipartiteMatcher.maximumMatching(warehouses, stores, edges);
        System.out.println("M4 bipartite matching size: " + matching.cardinality + " " + matching.pairs);

        RetailDataStore ds = new DataLoader("data").loadAll();
        InventoryFlowResult result = new InventoryFlowEngine(ds).planProductShipments("P0008");
        System.out.println("M4 inventory flow for P0008: " + result.fulfilledDemand + "/" + result.totalDemand);
    }

    private static FlowNetwork sampleNetwork() {
        FlowNetwork network = new FlowNetwork(6);
        network.addEdge(0, 1, 16);
        network.addEdge(0, 2, 13);
        network.addEdge(1, 2, 10);
        network.addEdge(2, 1, 4);
        network.addEdge(1, 3, 12);
        network.addEdge(3, 2, 9);
        network.addEdge(2, 4, 14);
        network.addEdge(4, 3, 7);
        network.addEdge(3, 5, 20);
        network.addEdge(4, 5, 4);
        return network;
    }
}
