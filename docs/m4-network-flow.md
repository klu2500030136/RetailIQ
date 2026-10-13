# M4 - Network Flow Engine

## Objective
Implement graph flow algorithms for RetailIQ supply-chain problems such as inventory routing, warehouse-to-store assignment, and bipartite matching.

## Implemented Algorithms

### Edmonds-Karp
- Uses BFS to find shortest augmenting paths in the residual graph.
- Time Complexity: O(VE^2)
- Retail Use Case: Explainable max-flow planning on smaller shipment networks.

### Dinic
- Builds level graphs and sends blocking flow through admissible edges.
- Time Complexity: O(V^2E) general case.
- Retail Use Case: Faster inventory shipment planning across many warehouses and stores.

### Bipartite Matching
- Reduces warehouse-store assignment to max flow.
- Time Complexity: dominated by the selected max-flow routine.
- Retail Use Case: One-to-one assignment feasibility checks.

### Inventory Flow Engine
- Builds a product-specific flow network:
  - source to warehouses by available inventory,
  - warehouses to stores by feasible shipment capacity,
  - stores to sink by store demand.
- Output: fulfilled demand and concrete shipment plan.

## Tests
`M4FlowTests` covers:
- classic max-flow correctness,
- Edmonds-Karp and Dinic agreement,
- bipartite matching cardinality,
- integration with the RetailIQ CSV dataset.
