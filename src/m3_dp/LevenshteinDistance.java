package m3_dp;

public class LevenshteinDistance {
    public static int distance(String source, String target) {
        if (source == null) source = "";
        if (target == null) target = "";
        
        int n = source.length();
        int m = target.length();
        
        int[][] dp = new int[n + 1][m + 1];
        
        // Base cases
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        
        // Recurrence
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = (source.charAt(i - 1) == target.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = DPUtils.min(
                    dp[i - 1][j] + 1,       // deletion
                    dp[i][j - 1] + 1,       // insertion
                    dp[i - 1][j - 1] + cost // substitution
                );
            }
        }
        
        return dp[n][m];
    }
    
    public static DistanceResult calculate(String source, String target) {
        return new DistanceResult(source, target, distance(source, target));
    }
}
