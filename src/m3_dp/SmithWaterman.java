package m3_dp;

public class SmithWaterman {
    private final int matchScore;
    private final int mismatchPenalty;
    private final int gapPenalty;

    public SmithWaterman(int matchScore, int mismatchPenalty, int gapPenalty) {
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
        int maxScore = 0;
        int maxI = 0, maxJ = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int score = (seqA.charAt(i - 1) == seqB.charAt(j - 1)) ? matchScore : mismatchPenalty;
                
                int val = DPUtils.max(
                    0,
                    dp[i - 1][j - 1] + score, // Diagonal
                    dp[i - 1][j] + gapPenalty, // Up
                    dp[i][j - 1] + gapPenalty  // Left
                );
                dp[i][j] = val;
                
                if (val > maxScore) {
                    maxScore = val;
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        
        // Traceback from maxI, maxJ until dp[i][j] == 0
        StringBuilder alignA = new StringBuilder();
        StringBuilder alignB = new StringBuilder();
        int i = maxI, j = maxJ;
        
        while (i > 0 && j > 0 && dp[i][j] > 0) {
            int score = (seqA.charAt(i - 1) == seqB.charAt(j - 1)) ? matchScore : mismatchPenalty;
            if (dp[i][j] == dp[i - 1][j - 1] + score) {
                alignA.append(seqA.charAt(i - 1));
                alignB.append(seqB.charAt(j - 1));
                i--; j--;
            } else if (dp[i][j] == dp[i - 1][j] + gapPenalty) {
                alignA.append(seqA.charAt(i - 1));
                alignB.append('-');
                i--;
            } else {
                alignA.append('-');
                alignB.append(seqB.charAt(j - 1));
                j--;
            }
        }
        
        int startA = i;
        int startB = j;
        
        return new AlignmentResult(seqA, seqB, alignA.reverse().toString(), alignB.reverse().toString(), maxScore, startA, maxI, startB, maxJ);
    }
}
