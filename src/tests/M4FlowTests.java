package tests;

import core.DataLoader;
import core.RetailDataStore;
import m4_flow.*;

public class M4FlowTests {
    public static void main(String[] args) {
        System.out.println("Running M4 Flow Tests...\n");
        boolean allPassed = true;
        allPassed &= testEdmondsKarp();
        allPassed &= testDinic();
        allPassed &= testBipartiteMatching();
        allPassed &= testInventoryFlow();
        System.out.println("\nTests Complete: " + (allPassed ? "All Passed" : "Some Failed"));
    }

    private static boolean testEdmondsKarp() {
        try {
            MaxFlowResult result = EdmondsKarp.maxFlow(classicNetwork(), 0, 5);
            if (result.maxFlow != 23) throw new RuntimeException("Expected 23, got " + result.maxFlow);
            System.out.println("[PASS] EdmondsKarp");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] EdmondsKarp: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDinic() {
        try {
            MaxFlowResult result = Dinic.maxFlow(classicNetwork(), 0, 5);
            if (result.maxFlow != 23) throw new RuntimeException("Expected 23, got " + result.maxFlow);
            System.out.println("[PASS] Dinic");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Dinic: " + e.getMessage());
            return false;
        }
    }

    private static boolean testBipartiteMatching() {
        try {
            String[] left = {"A", "B", "C"};
            String[] right = {"X", "Y", "Z"};
            boolean[][] canMatch = {
                    {true, false, false},
                    {true, true, false},
                    {false, true, true}
            };
            MatchingResult result = BipartiteMatcher.maximumMatching(left, right, canMatch);
            if (result.cardinality != 3) throw new RuntimeException("Expected perfect matching");
            System.out.println("[PASS] BipartiteMatcher");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] BipartiteMatcher: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInventoryFlow() {
        try {
            RetailDataStore ds = new DataLoader("data").loadAll();
            InventoryFlowResult result = new InventoryFlowEngine(ds).planProductShipments("P0008");
            if (result.fulfilledDemand <= 0) throw new RuntimeException("Expected positive flow for P0008");
            if (result.fulfilledDemand != result.totalSupply) throw new RuntimeException("Expected to ship all available supply");
            if (result.shipments.isEmpty()) throw new RuntimeException("No shipment plan produced");
            System.out.println("[PASS] Retail Inventory Flow");
            return true;
        } catch (Exception e) {
            System.out.println("[FAIL] Retail Inventory Flow: " + e.getMessage());
            return false;
        }
    }

    private static FlowNetwork classicNetwork() {
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
