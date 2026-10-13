package m3_dp;

public class DPUtils {
    /**
     * DP Foundation Components:
     * 1. State: What does dp[i][j] represent?
     * 2. Recurrence: How is dp[i][j] calculated from previous states?
     * 3. Base Case: What are the initial conditions?
     * 4. Transition: Moving through the state space.
     */
    
    public static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
    
    public static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static int max(int a, int b, int c, int d) {
        return Math.max(a, max(b, c, d));
    }
}
