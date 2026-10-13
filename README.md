# RetailIQ – Intelligent Retail Analytics Platform

## Project Objective
RetailIQ is an academic Data Structures and Algorithms (DSA-3) project that aims to build an intelligent retail analytics platform from scratch, using hand-built advanced algorithms rather than standard library equivalents.

## Problem Statement
The platform processes various retail data streams to provide meaningful analytics and intelligent capabilities such as product search, keyword analysis, inventory flow management, and constraint analysis.

## Inputs
- Products
- Customers
- Reviews
- Transactions
- Warehouses
- Stores
- Inventory
- Activity

## Outputs
- Product search results
- Corrected search queries
- Keyword analysis
- Review analysis
- Document similarity
- Product combinations
- Inventory flow
- Warehouse assignment
- Constraint analysis
- Sales rankings
- Activity samples
- Sales aggregates

## Six DSA Modules
The core intelligence of the platform will be implemented across six specialized modules:
- **M1:** String Algorithms (e.g., KMP, Z-Function, Rabin-Karp, Aho-Corasick)
- **M2:** Suffix Structures / Document Similarity (e.g., Suffix Arrays, Suffix Trees, Suffix Automata)
- **M3:** Advanced Dynamic Programming (e.g., Edit Distance, Sequence Alignment, Bitmask DP, Tree DP)
- **M4:** Network Flow (e.g., Ford-Fulkerson, Edmonds-Karp, Dinic, Bipartite Matching)
- **M5:** NP-Completeness & Approximation (e.g., SAT, CLIQUE, Set Cover, Approximation Algorithms)
- **M6:** Randomized & Parallel Algorithms (e.g., Randomized QuickSort, Parallel Reduce, Blelloch Scan)

## Architecture
See detailed explanation in `docs/architecture.md`.

## Current Implementation Status
RetailIQ now includes all six planned DSA modules.
- Implemented: Data models, CSV data loading, CLI menus, M1 string algorithms, M2 suffix structures, M3 dynamic programming, M4 network flow, M5 NP-completeness/approximation, and M6 randomized/parallel algorithms.
- Tests: foundational tests plus module-level tests for M1-M6.

## How to Run the Project
From the project root in Windows Command Prompt, compile `Main.java` together with the CLI files that contain additional package-private menu classes, then run the application:
```cmd
javac -d bin -sourcepath src src\Main.java src\cli\Menus1.java src\cli\Menus2.java src\cli\M456Menus.java && java -cp bin Main
```

## How to Run Tests
1. Compile the tests:
   ```bash
   javac -d bin $(find src -name '*.java')
   ```
2. Run the test suite:
   ```bash
   java -cp bin tests.Phase0Tests
   java -cp bin tests.M1StringTests
   java -cp bin tests.M2SuffixTests
   java -cp bin tests.M3DPTests
   java -cp bin tests.M4FlowTests
   java -cp bin tests.M5NPTests
   java -cp bin tests.M6RandomParallelTests
   ```

## Completed Implementation Roadmap
- **Phase 1 (M1):** Implement String Algorithms for product search and keyword analysis.
- **Phase 2 (M2):** Implement Suffix Structures for document similarity and advanced text queries.
- **Phase 3 (M3):** Implement Advanced DP for sequence alignments (e.g., spelling correction) and product combination optimizations.
- **Phase 4 (M4):** Implement Network Flow for inventory routing and warehouse assignment.
- **Phase 5 (M5):** Address computationally hard retail constraints via Approximation and NP-Complete reductions.
- **Phase 6 (M6):** Scale performance using Randomized & Parallel Algorithms.

## Module Documentation
- `docs/m1-string-algorithms.md`
- `docs/m2-suffix-structures.md`
- `docs/m3-advanced-dp.md`
- `docs/m4-network-flow.md`
- `docs/m5-np-approximation.md`
- `docs/m6-randomized-parallel.md`
