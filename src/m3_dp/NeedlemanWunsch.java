package m3_dp;

public class NeedlemanWunsch {
    private final int matchScore;
    private final int mismatchPenalty;
    private final int gapPenalty;

    public NeedlemanWunsch(int matchScore, int mismatchPenalty, int gapPenalty) {
        this.matchScore = matchScore;
        this.mismatchPenalty = mismatchPenalty;
        this.gapPenalty = gapPenalty;
    }

    public AlignmentResult align(String seqA, String seqB) {
        if (seqA == null) seqA = "";
        if (seqB == null) seqB = "";
        
        int n = seqA.length();
        int m = seqB.length();
        
        int[][] dp = new int[n + 1][m + 1];
        
        for (int i = 0; i <= n; i++) dp[i][0] = i * gapPenalty;
        for (int j = 0; j <= m; j++) dp[0][j] = j * gapPenalty;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int score = (seqA.charAt(i - 1) == seqB.charAt(j - 1)) ? matchScore : mismatchPenalty;
                dp[i][j] = DPUtils.max(
                    dp[i - 1][j - 1] + score, // Diagonal
                    dp[i - 1][j] + gapPenalty, // Up
                    dp[i][j - 1] + gapPenalty  // Left
                );
            }
        }
        
        int score = dp[n][m];
        
        // Traceback
        StringBuilder alignA = new StringBuilder();
        StringBuilder alignB = new StringBuilder();
        int i = n, j = m;
        
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0) {
                int diagScore = (seqA.charAt(i - 1) == seqB.charAt(j - 1)) ? matchScore : mismatchPenalty;
                if (dp[i][j] == dp[i - 1][j - 1] + diagScore) {
                    alignA.append(seqA.charAt(i - 1));
                    alignB.append(seqB.charAt(j - 1));
                    i--; j--;
                    continue;
                }
            }
            if (i > 0 && dp[i][j] == dp[i - 1][j] + gapPenalty) {
                alignA.append(seqA.charAt(i - 1));
                alignB.append('-');
                i--;
                continue;
            }
            if (j > 0 && dp[i][j] == dp[i][j - 1] + gapPenalty) {
                alignA.append('-');
                alignB.append(seqB.charAt(j - 1));
                j--;
            }
        }
        
        return new AlignmentResult(seqA, seqB, alignA.reverse().toString(), alignB.reverse().toString(), score, 0, n, 0, m);
    }
}
