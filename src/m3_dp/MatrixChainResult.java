package m3_dp;

public class MatrixChainResult {
    public final long minimumCost;
    public final String optimalParenthesization;

    public MatrixChainResult(long minimumCost, String optimalParenthesization) {
        this.minimumCost = minimumCost;
        this.optimalParenthesization = optimalParenthesization;
    }
}
