package m3_dp;

public class MatrixChainMultiplication {
    
    /**
     * @param p Array of dimensions. Matrix Ai has dimension p[i-1] x p[i].
     */
    public static MatrixChainResult optimize(int[] p) {
        int n = p.length - 1;
        if (n <= 0) return new MatrixChainResult(0, "");
        if (n == 1) return new MatrixChainResult(0, "A1");
        
        long[][] m = new long[n + 1][n + 1];
        int[][] s = new int[n + 1][n + 1];
        
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        
        for (int l = 2; l <= n; l++) { // chain length
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Long.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    long q = m[i][k] + m[k + 1][j] + (long)p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        
        String paren = constructOptimalParens(s, 1, n);
        return new MatrixChainResult(m[1][n], paren);
    }
    
    private static String constructOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            return "A" + i;
        } else {
            return "(" + constructOptimalParens(s, i, s[i][j]) + 
                   " x " + 
                   constructOptimalParens(s, s[i][j] + 1, j) + ")";
        }
    }
}
