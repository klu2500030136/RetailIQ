package m3_dp;

public class WeightedEditDistance {
    private final int insertCost;
    private final int deleteCost;
    private final int replaceCost;

    public WeightedEditDistance(int insertCost, int deleteCost, int replaceCost) {
        this.insertCost = insertCost;
        this.deleteCost = deleteCost;
        this.replaceCost = replaceCost;
    }

    public int distance(String source, String target) {
        if (source == null) source = "";
        if (target == null) target = "";
        
        int n = source.length();
        int m = target.length();
        
        int[][] dp = new int[n + 1][m + 1];
        
        for (int i = 0; i <= n; i++) dp[i][0] = i * deleteCost;
        for (int j = 0; j <= m; j++) dp[0][j] = j * insertCost;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = (source.charAt(i - 1) == target.charAt(j - 1)) ? 0 : replaceCost;
                dp[i][j] = DPUtils.min(
                    dp[i - 1][j] + deleteCost,
                    dp[i][j - 1] + insertCost,
                    dp[i - 1][j - 1] + cost
                );
            }
        }
        
        return dp[n][m];
    }
    
    public DistanceResult calculate(String source, String target) {
        return new DistanceResult(source, target, distance(source, target));
    }
}
