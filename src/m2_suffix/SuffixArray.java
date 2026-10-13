package m2_suffix;

import java.util.ArrayList;
import java.util.List;

public class SuffixArray {
    
    private final String text;
    private final int[] sa;
    
    private static class SuffixRank {
        int index;
        int rank0;
        int rank1;
    }
    
    public SuffixArray(String text) {
        this.text = text;
        this.sa = buildSuffixArray(text);
    }
    
    public String getText() {
        return text;
    }
    
    public int[] getSuffixArray() {
        return sa;
    }
    
    private int[] buildSuffixArray(String s) {
        int n = s.length();
        if (n == 0) return new int[0];
        if (n == 1) return new int[]{0};
        
        SuffixRank[] suffixes = new SuffixRank[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = new SuffixRank();
            suffixes[i].index = i;
            suffixes[i].rank0 = s.charAt(i);
            suffixes[i].rank1 = ((i + 1) < n) ? s.charAt(i + 1) : -1;
        }
        
        // Initial manual merge sort
        sortSuffixes(suffixes, n);
        
        int[] ind = new int[n];
        for (int k = 4; k < 2 * n; k *= 2) {
            int rank = 0;
            int prevRank0 = suffixes[0].rank0;
            int prevRank1 = suffixes[0].rank1;
            suffixes[0].rank0 = rank;
            ind[suffixes[0].index] = 0;
            
            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank0 == prevRank0 && suffixes[i].rank1 == prevRank1) {
                    prevRank0 = suffixes[i].rank0;
                    prevRank1 = suffixes[i].rank1;
                    suffixes[i].rank0 = rank;
                } else {
                    prevRank0 = suffixes[i].rank0;
                    prevRank1 = suffixes[i].rank1;
                    suffixes[i].rank0 = ++rank;
                }
                ind[suffixes[i].index] = i;
            }
            
            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + k / 2;
                suffixes[i].rank1 = (nextIndex < n) ? suffixes[ind[nextIndex]].rank0 : -1;
            }
            
            sortSuffixes(suffixes, n);
        }
        
        int[] saResult = new int[n];
        for (int i = 0; i < n; i++) {
            saResult[i] = suffixes[i].index;
        }
        return saResult;
    }
    
    private void sortSuffixes(SuffixRank[] arr, int n) {
        // Quick custom sort for Prefix Doubling array
        java.util.Arrays.sort(arr, (a, b) -> {
            if (a.rank0 != b.rank0) return Integer.compare(a.rank0, b.rank0);
            return Integer.compare(a.rank1, b.rank1);
        });
    }

    public List<Integer> search(String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || sa.length == 0) return matches;
        
        int n = text.length();
        
        // Find left boundary
        int l = 0, r = n - 1;
        int leftMatch = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int cmp = compareSuffixWithPattern(sa[mid], pattern);
            if (cmp == 0) {
                leftMatch = mid;
                r = mid - 1; // keep looking left
            } else if (cmp < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        
        if (leftMatch == -1) return matches; // No match found
        
        // Find right boundary
        l = 0; r = n - 1;
        int rightMatch = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int cmp = compareSuffixWithPattern(sa[mid], pattern);
            if (cmp == 0) {
                rightMatch = mid;
                l = mid + 1; // keep looking right
            } else if (cmp < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        
        for (int i = leftMatch; i <= rightMatch; i++) {
            matches.add(sa[i]);
        }
        
        return matches;
    }

    private int compareSuffixWithPattern(int suffixIndex, String pattern) {
        int m = pattern.length();
        int n = text.length();
        int i = 0;
        while (i < m && suffixIndex + i < n) {
            char t = text.charAt(suffixIndex + i);
            char p = pattern.charAt(i);
            if (t != p) {
                return Character.compare(t, p);
            }
            i++;
        }
        // If we matched all characters of pattern, it's a match
        if (i == m) return 0;
        // If pattern is longer, suffix is smaller
        return -1;
    }
}
