package m2_suffix;

import java.util.Arrays;

public class SAIS {
    
    // SA-IS suffix array construction.
    public static int[] build(String s) {
        int n = s.length();
        if (n == 0) return new int[0];
        if (n == 1) return new int[]{0};
        
        int[] sa = new int[n];
        // Note: Full SA-IS implementation for ASCII (0-255).
        // To make it fully robust for educational purposes, we convert the string to integer array.
        int[] text = new int[n + 1];
        for(int i=0; i<n; i++) text[i] = s.charAt(i);
        text[n] = 0; // sentinel
        
        int[] saFull = sais(text, 256);
        // exclude sentinel
        for(int i=0; i<n; i++) sa[i] = saFull[i+1];
        return sa;
    }

    private static int[] sais(int[] text, int K) {
        int n = text.length;
        boolean[] t = new boolean[n]; // t[i]=true means S-type, false means L-type
        t[n - 1] = true;
        for (int i = n - 2; i >= 0; i--) {
            if (text[i] < text[i + 1]) {
                t[i] = true;
            } else if (text[i] > text[i + 1]) {
                t[i] = false;
            } else {
                t[i] = t[i + 1];
            }
        }

        // Find LMS characters
        int[] p1 = new int[n];
        int n1 = 0;
        for (int i = 1; i < n; i++) {
            if (isLMS(t, i)) {
                p1[n1++] = i;
            }
        }
        p1 = Arrays.copyOf(p1, n1);

        int[] sa = new int[n];
        Arrays.fill(sa, -1);
        inducedSort(text, sa, p1, t, K);

        // Name LMS substrings
        int[] sa1 = new int[n1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (isLMS(t, sa[i])) {
                sa1[j++] = sa[i];
            }
        }

        int[] name = new int[n];
        Arrays.fill(name, -1);
        int currentName = 0;
        name[sa1[0]] = currentName;
        int prevLMS = sa1[0];
        
        for (int i = 1; i < n1; i++) {
            int pos = sa1[i];
            boolean diff = false;
            for (int d = 0; d < n; d++) {
                if (text[pos + d] != text[prevLMS + d] || isLMS(t, pos + d) != isLMS(t, prevLMS + d)) {
                    diff = true;
                    break;
                } else if (d > 0 && (isLMS(t, pos + d) || isLMS(t, prevLMS + d))) {
                    break;
                }
            }
            if (diff) {
                currentName++;
                prevLMS = pos;
            }
            name[pos] = currentName;
        }

        // Construct reduced problem
        int[] s1 = new int[n1];
        j = 0;
        for (int i = 0; i < n; i++) {
            if (name[i] != -1) {
                s1[j++] = name[i];
            }
        }

        // Solve reduced problem
        int[] sa1Final;
        if (currentName + 1 < n1) {
            sa1Final = sais(s1, currentName + 1);
        } else {
            sa1Final = new int[n1];
            for (int i = 0; i < n1; i++) {
                sa1Final[s1[i]] = i;
            }
        }

        // Final induced sort
        int[] p1Final = new int[n1];
        for (int i = 0; i < n1; i++) {
            p1Final[i] = p1[sa1Final[i]];
        }
        Arrays.fill(sa, -1);
        inducedSort(text, sa, p1Final, t, K);
        
        return sa;
    }

    private static boolean isLMS(boolean[] t, int i) {
        return i > 0 && t[i] && !t[i - 1];
    }

    private static void getBuckets(int[] text, int[] b, int K, boolean end) {
        int[] count = new int[K];
        for (int c : text) {
            count[c]++;
        }
        int sum = 0;
        for (int i = 0; i < K; i++) {
            sum += count[i];
            b[i] = end ? sum - 1 : sum - count[i];
        }
    }

    private static void inducedSort(int[] text, int[] sa, int[] p1, boolean[] t, int K) {
        int n = text.length;
        int[] b = new int[K];
        getBuckets(text, b, K, true);
        
        for (int i = p1.length - 1; i >= 0; i--) {
            int j = p1[i];
            sa[b[text[j]]--] = j;
        }

        getBuckets(text, b, K, false);
        for (int i = 0; i < n; i++) {
            int j = sa[i] - 1;
            if (j >= 0 && !t[j]) {
                sa[b[text[j]]++] = j;
            }
        }

        getBuckets(text, b, K, true);
        for (int i = n - 1; i >= 0; i--) {
            int j = sa[i] - 1;
            if (j >= 0 && t[j]) {
                sa[b[text[j]]--] = j;
            }
        }
    }
}
