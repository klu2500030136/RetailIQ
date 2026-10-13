# M5 - NP-Completeness & Approximation Engine

## Objective
Model computationally hard retail decisions and provide exact small-instance solvers or approximation algorithms where exhaustive search is not practical.

## Implemented Algorithms

### Greedy Set Cover
- Selects products that cover requested categories with a cost-aware greedy rule.
- Handles singular/plural category input by canonicalizing category names.
- Retail Use Case: finding a small representative product bundle that covers required categories.
- Approximation: standard greedy set-cover heuristic.

### Approximate Knapsack
- Greedily ranks products by rating/sales value density under a budget.
- Retail Use Case: choosing a high-value product portfolio without exponential DP.

### Simple SAT Solver
- Backtracking solver for small CNF formulas.
- Retail Use Case: academic demonstration of NP-complete constraint modeling.
- Note: intended for small reductions, not industrial SAT solving.

### Vertex Cover 2-Approximation
- Repeatedly selects both endpoints of an uncovered edge.
- Retail Use Case: selecting monitoring points that cover dependency/conflict edges.

## Tests
`M5NPTests` covers set cover, budget preservation, satisfiable and unsatisfiable SAT formulas, and vertex-cover edge coverage.
