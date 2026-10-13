# M1 - String Algorithm Engine

## 1. Objective
Build a reusable String Algorithm Engine for RetailIQ to perform product searches, review analysis, and transaction/product code tracking without relying on built-in substring libraries like `indexOf` or regular expressions.

## 2. RetailIQ Use Cases
- **Product Search (KMP):** Finding products matching customer query strings.
- **Review Phrase Search (Z-Function):** Discovering repeating phrases (e.g., "battery life") inside lengthy product reviews.
- **Code Search (Rabin-Karp):** Finding specific substrings inside product/transaction codes (e.g., matching "IPH15").
- **Multi-Keyword Analysis (Aho-Corasick):** Scanning reviews for occurrences of multiple keywords simultaneously (e.g., "wireless", "gaming", "premium").

## 3. Naive Algorithm
Implemented via `NaiveMatcher`. Uses two nested loops to check each possible text offset.
- Time Complexity: O(nm) worst case, where n = text length, m = pattern length.
- Space Complexity: O(1) excluding output.

## 4. KMP Algorithm
Implemented via `KMPMatcher`. Builds the Longest Prefix Suffix (LPS) array to skip redundant comparisons.
- Preprocessing Time: O(m)
- Search Time: O(n)
- Space Complexity: O(m) for the LPS array.

## 5. Z-Function
Implemented via `ZFunction`. Computes the Z-array for string `pattern + "$" + text` to find bounds.
- Time Complexity: O(n + m) overall.
- Space Complexity: O(n + m) for Z-array.

## 6. Rabin-Karp Algorithm
Implemented via `RabinKarpMatcher`. Uses a rolling hash (Base 256, Modulo 101). Explicitly checks hash collisions.
- Average Time Complexity: O(n + m)
- Worst Case Time Complexity: O(nm)
- Space Complexity: O(1) excluding output.

## 7. Aho-Corasick Algorithm
Implemented via `AhoCorasick` and `TrieNode`. Builds a Trie and computes BFS-based failure links to process text in linear time against multiple patterns.
- Build Time: O(Total length of all patterns)
- Search Time: O(n + matches)
- Space Complexity: O(Total length of all patterns x Alphabet representation).

## 8. Integration Architecture
`StringSearchEngine` connects the data layer (`RetailDataStore`) to the algorithms. It routes specific queries to the optimal algorithm component and packages results.

## 9. Complexity Table
| Algorithm      | Setup Time | Search Time (Avg) | Search Time (Worst) | Space (Excl. Out) | Use Case                      |
|----------------|------------|-------------------|---------------------|-------------------|-------------------------------|
| Naive          | O(1)       | O(nm)             | O(nm)               | O(1)              | Baseline comparison           |
| KMP            | O(m)       | O(n)              | O(n)                | O(m)              | Product search                |
| Z-Function     | O(1)       | O(n+m)            | O(n+m)              | O(n+m)            | Review phrase detection       |
| Rabin-Karp     | O(m)       | O(n)              | O(nm)               | O(1)              | Code prefix/substring search  |
| Aho-Corasick   | O(Σm)      | O(n)              | O(n + z)            | O(Σm)             | Multi-keyword review analysis |

*Note: z is the number of matches found.*

## 10. Test Cases
The `M1StringTests` class covers:
- Boundary conditions (empty strings, pattern > text)
- Multiple and overlapping matches
- Exact matches and no matches
- Cross-algorithm consistency validations
- Integration testing with the RetailIQ datasets

## 11. Benchmark Methodology
A simple benchmark `M1Demo` executes `Naive`, `KMP`, and `Rabin-Karp` over 100,000 iterations to measure average nanosecond/millisecond times.

## 12. Current Implementation Status
- [x] Phase 0 Foundation
- [x] Phase 1 Core/Data Engine
- [x] M1 String Algorithm Engine
- [x] M2 Suffix Structures / Document Similarity
- [x] M3 Advanced Dynamic Programming
- [x] M4 Network Flow
- [x] M5 NP-Completeness & Approximation
- [x] M6 Randomized & Parallel Algorithms
