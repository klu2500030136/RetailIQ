package cli;

import core.RetailDataStore;
import m4_flow.*;
import m5_np.*;
import m6_random_parallel.*;
import model.Activity;
import model.Product;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class M4Menu {
    private final InputHelper input;
    private final RetailDataStore dataStore;

    M4Menu(InputHelper input, RetailDataStore dataStore) {
        this.input = input;
        this.dataStore = dataStore;
    }

    public void show() {
        while (true) {
            System.out.println("\n========== M4 NETWORK FLOW ==========");
            System.out.println("1. Inventory Shipment Plan");
            System.out.println("2. Bipartite Warehouse-Store Matching");
            System.out.println("3. Max Flow Lab");
            System.out.println("4. M4 Benchmark / Demo");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 4);
            if (c == 0) return;
            switch (c) {
                case 1: shipmentPlan(); break;
                case 2: matchingDemo(); break;
                case 3: maxFlowLab(); break;
                case 4: M4Demo.main(new String[0]); input.pause(); break;
            }
        }
    }

    private void shipmentPlan() {
        String productId = input.readString("Product ID: ");
        InventoryFlowResult result = new InventoryFlowEngine(dataStore).planProductShipments(productId);
        System.out.println("\nSupply: " + result.totalSupply);
        System.out.println("Demand: " + result.totalDemand);
        System.out.println("Fulfilled: " + result.fulfilledDemand);
        for (ShipmentPlan plan : result.shipments) System.out.println("  " + plan);
        input.pause();
    }

    private void matchingDemo() {
        String[] warehouses = {"W1", "W2", "W3"};
        String[] stores = {"S1", "S2", "S3"};
        boolean[][] canMatch = {
                {true, true, false},
                {false, true, true},
                {true, false, true}
        };
        MatchingResult result = BipartiteMatcher.maximumMatching(warehouses, stores, canMatch);
        System.out.println("Matching size: " + result.cardinality);
        for (String pair : result.pairs) System.out.println("  " + pair);
        input.pause();
    }

    private void maxFlowLab() {
        FlowNetwork network = new FlowNetwork(4);
        network.addEdge(0, 1, input.readInt("Capacity source->A: ", 0, 100000));
        network.addEdge(0, 2, input.readInt("Capacity source->B: ", 0, 100000));
        network.addEdge(1, 2, input.readInt("Capacity A->B: ", 0, 100000));
        network.addEdge(1, 3, input.readInt("Capacity A->sink: ", 0, 100000));
        network.addEdge(2, 3, input.readInt("Capacity B->sink: ", 0, 100000));
        MaxFlowResult result = EdmondsKarp.maxFlow(network, 0, 3);
        System.out.println("Max flow: " + result.maxFlow);
        System.out.println("Augmenting paths: " + result.augmentingPathCount);
        input.pause();
    }
}

class M5Menu {
    private final InputHelper input;
    private final RetailDataStore dataStore;

    M5Menu(InputHelper input, RetailDataStore dataStore) {
        this.input = input;
        this.dataStore = dataStore;
    }

    public void show() {
        while (true) {
            System.out.println("\n========== M5 NP-COMPLETENESS & APPROXIMATION ==========");
            System.out.println("1. Greedy Category Set Cover");
            System.out.println("2. Approximate Product Portfolio");
            System.out.println("3. Small SAT Solver");
            System.out.println("4. Vertex Cover Approximation");
            System.out.println("5. M5 Benchmark / Demo");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 5);
            if (c == 0) return;
            switch (c) {
                case 1: setCover(); break;
                case 2: portfolio(); break;
                case 3: satDemo(); break;
                case 4: vertexCoverDemo(); break;
                case 5: M5Demo.main(new String[0]); input.pause(); break;
            }
        }
    }

    private void setCover() {
        String raw = input.readString("Required categories (comma separated): ");
        Set<String> required = new HashSet<>();
        for (String part : raw.split(",")) if (!part.trim().isEmpty()) required.add(part.trim());
        SetCoverResult result = GreedySetCover.coverCategories(dataStore.getProducts(), required);
        System.out.println("Complete cover: " + result.complete);
        System.out.println("Total cost: $" + String.format("%.2f", result.totalCost));
        for (Product product : result.selectedProducts) {
            System.out.println("  " + product.getProductId() + " " + product.getCategory() + " $" + product.getPrice());
        }
        input.pause();
    }

    private void portfolio() {
        int budget = input.readInt("Budget: ", 0, 100000);
        KnapsackResult result = ApproximateKnapsack.greedyByRatingSales(dataStore.getProducts(), budget);
        System.out.println("Total cost: $" + String.format("%.2f", result.totalCost));
        System.out.println("Total value: " + String.format("%.2f", result.totalValue));
        for (Product product : result.selectedProducts) {
            System.out.println("  " + product.getProductId() + " " + product.getName());
        }
        input.pause();
    }

    private void satDemo() {
        CNFFormula formula = new CNFFormula(3);
        formula.addClause(1, -2);
        formula.addClause(2, 3);
        formula.addClause(-1, -3);
        SatResult result = SimpleSatSolver.solve(formula);
        System.out.println("Formula: (x1 OR !x2) AND (x2 OR x3) AND (!x1 OR !x3)");
        System.out.println("Satisfiable: " + result.satisfiable);
        if (result.satisfiable) {
            System.out.println("Assignment: x1=" + result.assignment[1] + ", x2=" + result.assignment[2] + ", x3=" + result.assignment[3]);
        }
        input.pause();
    }

    private void vertexCoverDemo() {
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        boolean[] cover = VertexCoverApproximation.twoApproximation(5, edges);
        System.out.println("Edges: (0,1), (1,2), (2,3), (3,4)");
        System.out.println("Approx cover: " + VertexCoverApproximation.selectedVertices(cover));
        input.pause();
    }
}

class M6Menu {
    private final InputHelper input;
    private final RetailDataStore dataStore;

    M6Menu(InputHelper input, RetailDataStore dataStore) {
        this.input = input;
        this.dataStore = dataStore;
    }

    public void show() {
        while (true) {
            System.out.println("\n========== M6 RANDOMIZED & PARALLEL ALGORITHMS ==========");
            System.out.println("1. Randomized QuickSort Sales Counts");
            System.out.println("2. Randomized QuickSelect");
            System.out.println("3. Reservoir Activity Sampling");
            System.out.println("4. Parallel Reduce + Blelloch Scan");
            System.out.println("5. M6 Benchmark / Demo");
            System.out.println("0. Back");
            int c = input.readInt("Enter choice: ", 0, 5);
            if (c == 0) return;
            switch (c) {
                case 1: randomizedSort(); break;
                case 2: quickSelect(); break;
                case 3: reservoirSample(); break;
                case 4: reduceAndScan(); break;
                case 5: M6Demo.main(new String[0]); input.pause(); break;
            }
        }
    }

    private void randomizedSort() {
        int[] sales = salesCounts();
        RandomizedQuickSort.sort(sales, 42L);
        System.out.println("Sorted sales counts: " + Arrays.toString(sales));
        input.pause();
    }

    private void quickSelect() {
        int[] sales = salesCounts();
        int k = input.readInt("k-th smallest index (0 to " + (sales.length - 1) + "): ", 0, sales.length - 1);
        int value = RandomizedQuickSelect.selectKthSmallest(sales, k, 42L);
        System.out.println("k-th smallest sales count: " + value);
        input.pause();
    }

    private void reservoirSample() {
        int k = input.readInt("Sample size: ", 0, dataStore.getActivities().size());
        List<Activity> sample = new ReservoirSampler<Activity>().sample(dataStore.getActivities(), k, 123L);
        for (Activity activity : sample) System.out.println("  " + activity);
        input.pause();
    }

    private void reduceAndScan() {
        int[] sales = salesCounts();
        System.out.println("Parallel sum: " + ParallelReducer.sum(sales, 4));
        System.out.println("Parallel max: " + ParallelReducer.max(sales, 4));
        System.out.println("Inclusive prefix: " + Arrays.toString(BlellochScan.inclusivePrefixSum(sales)));
        input.pause();
    }

    private int[] salesCounts() {
        int[] sales = new int[dataStore.getProducts().size()];
        for (int i = 0; i < sales.length; i++) sales[i] = dataStore.getProducts().get(i).getSalesCount();
        return sales;
    }
}
