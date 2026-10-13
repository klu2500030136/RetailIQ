package m3_dp;

public class AlignmentResult {
    public final String sequenceA;
    public final String sequenceB;
    public final String alignedA;
    public final String alignedB;
    public final int score;
    public final int startA;
    public final int endA;
    public final int startB;
    public final int endB;

    public AlignmentResult(String sequenceA, String sequenceB, String alignedA, String alignedB, 
                           int score, int startA, int endA, int startB, int endB) {
        this.sequenceA = sequenceA;
        this.sequenceB = sequenceB;
        this.alignedA = alignedA;
        this.alignedB = alignedB;
        this.score = score;
        this.startA = startA;
        this.endA = endA;
        this.startB = startB;
        this.endB = endB;
    }
}
