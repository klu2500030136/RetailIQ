package m5_np;

public class SatResult {
    public final boolean satisfiable;
    public final boolean[] assignment;

    public SatResult(boolean satisfiable, boolean[] assignment) {
        this.satisfiable = satisfiable;
        this.assignment = assignment;
    }
}
