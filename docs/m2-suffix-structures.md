# M2 - Suffix Structures & Document Similarity Engine

## 1. Objective
Extend RetailIQ beyond simple pattern matching into advanced substring structures capable of indexing large amounts of text (product descriptions, reviews) for similarity analysis and fast search.

## 2. Text Normalizer
Normalizes text to lowercase, removing non-alphanumeric punctuation and collapsing whitespace to allow deterministic substring queries unaffected by formatting.
- Complexity: $O(n)$ time and $O(n)$ space.

## 3. Suffix Array
Constructed using Prefix Doubling strategy.
- Time Complexity: $O(n \log^2 n)$ due to the custom sort at each step.
- Space Complexity: $O(n)$ aside from string representation.

## 4. Suffix Array Search
Uses Binary Search on the suffix array to find matches.
- Time Complexity: $O(m \log n)$ where $m$ is pattern length, since string comparison takes $O(m)$ at each step.

## 5. Kasai's LCP Array
Constructs the Longest Common Prefix array.
- Time Complexity: $O(n)$ 
- Space Complexity: $O(n)$ auxiliary.

## 6. Document Similarity Engine
Identifies longest common substrings and overlapping common phrases between documents by creating a concatenated string `DocA#DocB`, building a Suffix Array + LCP, and finding the maximum LCP values at the document boundary.
- Time Complexity: $O((n+m) \log^2 (n+m))$ due to SA build.
- Space Complexity: $O(n+m)$

## 7. Review Substring Search
Indexes a combined corpus of reviews. When searching, we map indices back to logical reviews.
- Search Time Complexity: $O(p \log N)$ where $p$ is phrase length, $N$ is total combined corpus length.

## 8. SA-IS
A standard implementation of the linear time SA-IS (Suffix Array - Induced Sorting) algorithm. It classifies LMS characters, induces sorts, recursively reduces, and induces again.
- Time Complexity: $O(n)$
- Space Complexity: $O(n)$ auxiliary

## 9. Suffix Automaton
An online construction of a DAWG (Directed Acyclic Word Graph) representing all substrings of a string.
- Time Complexity: $O(n)$ construction, $O(m)$ substring existence query.
- Space Complexity: bounded by $2n - 1$ states and $3n - 4$ transitions ($O(n)$).

## 10. Suffix Tree
An educational naive implementation using nested maps. 
- Time Complexity: $O(n^2)$ construction, $O(m)$ search.
- Space Complexity: $O(n^2)$ worst case memory for naive tree without edge compression.

## 11. Current Implementation Status
- [x] Phase 0 Foundation
- [x] Phase 1 Core/Data Engine
- [x] M1 String Algorithm Engine
- [x] M2 Suffix Structures / Document Similarity
- [x] M3 Advanced Dynamic Programming
- [x] M4 Network Flow
- [x] M5 NP-Completeness & Approximation
- [x] M6 Randomized & Parallel Algorithms
