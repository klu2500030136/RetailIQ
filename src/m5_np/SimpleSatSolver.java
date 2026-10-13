package m5_np;

public class SimpleSatSolver {
    public static SatResult solve(CNFFormula formula) {
        boolean[] assigned = new boolean[formula.getVariableCount() + 1];
        boolean[] values = new boolean[formula.getVariableCount() + 1];
        boolean ok = backtrack(formula, 1, assigned, values);
        return new SatResult(ok, ok ? values : new boolean[formula.getVariableCount() + 1]);
    }

    private static boolean backtrack(CNFFormula formula, int variable, boolean[] assigned, boolean[] values) {
        if (!partialConsistent(formula, assigned, values)) return false;
        if (variable > formula.getVariableCount()) return satisfied(formula, values);

        assigned[variable] = true;
        values[variable] = false;
        if (backtrack(formula, variable + 1, assigned, values)) return true;
        values[variable] = true;
        if (backtrack(formula, variable + 1, assigned, values)) return true;
        assigned[variable] = false;
        values[variable] = false;
        return false;
    }

    private static boolean partialConsistent(CNFFormula formula, boolean[] assigned, boolean[] values) {
        for (int[] clause : formula.getClauses()) {
            boolean hasTrue = false;
            boolean hasOpen = false;
            for (int literal : clause) {
                int variable = Math.abs(literal);
                if (!assigned[variable]) {
                    hasOpen = true;
                } else if ((literal > 0 && values[variable]) || (literal < 0 && !values[variable])) {
                    hasTrue = true;
                    break;
                }
            }
            if (!hasTrue && !hasOpen) return false;
        }
        return true;
    }

    private static boolean satisfied(CNFFormula formula, boolean[] values) {
        for (int[] clause : formula.getClauses()) {
            boolean ok = false;
            for (int literal : clause) {
                int variable = Math.abs(literal);
                if ((literal > 0 && values[variable]) || (literal < 0 && !values[variable])) {
                    ok = true;
                    break;
                }
            }
            if (!ok) return false;
        }
        return true;
    }
}
