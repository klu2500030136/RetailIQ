package m5_np;

import java.util.ArrayList;
import java.util.List;

public class CNFFormula {
    private final int variableCount;
    private final List<int[]> clauses = new ArrayList<>();

    public CNFFormula(int variableCount) {
        if (variableCount < 0) throw new IllegalArgumentException("Variable count cannot be negative");
        this.variableCount = variableCount;
    }

    public void addClause(int... literals) {
        clauses.add(literals);
    }

    public int getVariableCount() {
        return variableCount;
    }

    public List<int[]> getClauses() {
        return clauses;
    }
}
