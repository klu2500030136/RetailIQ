# M3 - Advanced Dynamic Programming Engine

## Objective
Implement various Dynamic Programming algorithms tailored for specific computational problems in RetailIQ. The focus is on Edit Distances (Levenshtein, Damerau, Weighted), Sequence Alignments (Needleman-Wunsch, Smith-Waterman), Optimization (Bitmask DP), and Matrix Chain Optimization.

## Edit Distances

### 1. Levenshtein Distance
- **Purpose**: Calculate the minimum operations (insert, delete, replace) to transform one string into another.
- **Retail Use Case**: Fuzzy product searching (matching typos to actual products).
- **Recurrence**: `dp[i][j] = min(dp[i-1][j]+1, dp[i][j-1]+1, dp[i-1][j-1] + (A[i] == B[j] ? 0 : 1))`
- **Time Complexity**: $O(nm)$
- **Space Complexity**: $O(nm)$ (implemented as full matrix for tracebacks if needed, though primarily returns distance).

### 2. Damerau-Levenshtein
- **Purpose**: Same as Levenshtein but allows transposition (swapping adjacent characters).
- **Recurrence**: Standard Levenshtein + `dp[i][j] = min(dp[i][j], dp[i-2][j-2] + cost)` when transposing.
- **Time Complexity**: $O(nm)$
- **Space Complexity**: $O(nm)$

### 3. Weighted Edit Distance
- **Purpose**: Allows custom costs for insertion, deletion, and replacement.
- **Recurrence**: `dp[i][j] = min(dp[i-1][j]+del, dp[i][j-1]+ins, dp[i-1][j-1]+rep)`
- **Time Complexity**: $O(nm)$
- **Space Complexity**: $O(nm)$

## Sequence Alignment

### 4. Needleman-Wunsch
- **Purpose**: Global sequence alignment between two strings.
- **Retail Use Case**: Comparing full product descriptions.
- **State**: `dp[i][j]` = max score aligning prefix $i$ and prefix $j$.
- **Recurrence**: `max(diag + match/mismatch, up + gap, left + gap)`
- **Time Complexity**: $O(nm)$
- **Space Complexity**: $O(nm)$

### 5. Smith-Waterman
- **Purpose**: Local sequence alignment (finding the most similar substring region).
- **Retail Use Case**: Finding common phrases or overlapping features between reviews/products.
- **State**: `dp[i][j]` = max local alignment score ending at $i, j$.
- **Recurrence**: `max(0, diag + match/mismatch, up + gap, left + gap)`
- **Time Complexity**: $O(nm)$
- **Space Complexity**: $O(nm)$

## Optimization Problems

### 6. Bitmask DP
- **Purpose**: Select an optimal subset of products meeting category requirements under a budget.
- **Retail Use Case**: Product Bundle Optimization.
- **State**: Bitmask representing selected items. `scoreDP[mask]` tracking categories covered.
- **Time Complexity**: $O(2^n \cdot n)$ where $n$ is candidate count.
- **Space Complexity**: $O(2^n)$
- **Note**: $n \le 20$ restriction enforced to prevent exponential memory/time explosion.

### 7. Matrix Chain Multiplication
- **Purpose**: Optimize the order of scalar multiplications for a chain of matrices.
- **Retail Use Case**: Optimization of retail analytics data processing.
- **Recurrence**: `dp[i][j] = min_{i \le k < j}(dp[i][k] + dp[k+1][j] + p_{i-1}p_kp_j)`
- **Time Complexity**: $O(n^3)$
- **Space Complexity**: $O(n^2)$
