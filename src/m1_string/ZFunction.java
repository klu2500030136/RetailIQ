package m1_string;

import java.util.ArrayList;
import java.util.List;

public class ZFunction {
    
    public static int[] computeZ(String text) {
        int n = text.length();
        int[] Z = new int[n];
        if (n == 0) return Z;
        
        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i > R) {
                L = R = i;
                while (R < n && text.charAt(R - L) == text.charAt(R)) {
                    R++;
                }
                Z[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if (Z[k] < R - i + 1) {
                    Z[i] = Z[k];
                } else {
                    L = i;
                    while (R < n && text.charAt(R - L) == text.charAt(R)) {
                        R++;
                    }
                    Z[i] = R - L;
                    R--;
                }
            }
        }
        return Z;
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || pattern.length() > text.length()) {
            return matches;
        }

        String combined = pattern + "$" + text;
        int[] z = computeZ(combined);
        int pLen = pattern.length();
        
        for (int i = 0; i < z.length; i++) {
            if (z[i] == pLen) {
                matches.add(i - pLen - 1);
            }
        }
        return matches;
    }
}
