# M6 - Randomized & Parallel Algorithms Engine

## Objective
Add randomized algorithms and simple parallel primitives to scale RetailIQ analytics and demonstrate probabilistic/parallel DSA concepts.

## Implemented Algorithms

### Randomized QuickSort
- Random pivot selection reduces sensitivity to adversarial input.
- Average Time Complexity: O(n log n)
- Worst Case Time Complexity: O(n^2)

### Randomized QuickSelect
- Finds the k-th smallest value without sorting the full array.
- Average Time Complexity: O(n)
- Worst Case Time Complexity: O(n^2)

### Reservoir Sampling
- Maintains a uniform sample from a stream of unknown length.
- Retail Use Case: sample activity logs without loading or sorting the full stream.

### Parallel Reduce
- Thread-based sum and max reductions over integer arrays.
- Retail Use Case: aggregate sales counters or inventory quantities.

### Blelloch Scan
- Work-efficient prefix sum implementation with power-of-two padding.
- Retail Use Case: cumulative sales/inventory summaries.

## Tests
`M6RandomParallelTests` covers sorting, selection, reservoir sample size, parallel sum/max, and prefix-sum correctness.
