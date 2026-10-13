# RetailIQ Architecture

## Project Structure

```text
RetailIQ/
├── data/                    # Placeholder for potential external data files
│   ├── products/
│   ├── customers/
│   ├── reviews/
│   ├── transactions/
│   ├── warehouses/
│   ├── stores/
│   ├── inventory/
│   └── activities/
├── src/                     # Main source code directory
│   ├── model/               # POJO data models for all retail entities
│   ├── core/                # Infrastructure for loading data and formatting results
│   ├── m1_string/           # String search algorithms
│   ├── m2_suffix/           # Suffix structures and similarity metrics
│   ├── m3_dp/               # Advanced Dynamic Programming techniques
│   ├── m4_flow/             # Network flow and max flow models
│   ├── m5_np/               # SAT reductions and approximation algorithms
│   ├── m6_random_parallel/  # Parallel scans, reductions, and randomized techniques
│   ├── benchmark/           # Performance benchmarking and time tracking
│   ├── tests/               # Foundational and algorithm correctness tests
│   └── Main.java            # Entry point for the analytics platform
├── docs/                    # Architectural and module-specific documentation
└── README.md                # Project overview and roadmap
```

## Data Model

The `src/model` package contains raw POJOs mapping exactly to retail domain entities. These models intentionally avoid dependencies on external frameworks (like Spring or JPA) and limit the use of heavy Java utilities to preserve an environment strictly suitable for custom Data Structures and Algorithms implementations.
- `Product`: Item catalog info (ID, name, stock, rating)
- `Customer`: Customer identity
- `Transaction`: Purchase events connecting customers to products
- `Review`: Feedback on products
- `Warehouse`, `Store`, `Inventory`: Supply chain management entities
- `Activity`: Customer action log (View, Search, Add-to-cart)

## Core Infrastructure
The `src/core` package serves as the plumbing connecting algorithms with the raw data.
- `DataLoader`: Supplies the realistic sample data needed by our engines.
- `QueryProcessor`: A common interface defining the kinds of requests the algorithms will handle.
- `ResultFormatter`: Standardizes console output across all algorithm phases.

## DSA Modules (M1-M6)
These are intentionally isolated packages designed as algorithm plug-ins. The modules represent the project's intellectual core. As an academic DSA project, algorithms within these modules are written natively using primitive arrays, loops, and basic structures where practical, avoiding built-in algorithm shortcuts for the core DSA work.
