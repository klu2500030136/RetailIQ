package m5_np;

import java.util.ArrayList;
import java.util.List;

public class VertexCoverApproximation {
    public static boolean[] twoApproximation(int vertexCount, int[][] edges) {
        boolean[] coveredVertex = new boolean[vertexCount];
        boolean[] edgeCovered = new boolean[edges.length];

        for (int i = 0; i < edges.length; i++) {
            if (edgeCovered[i]) continue;
            int u = edges[i][0];
            int v = edges[i][1];
            coveredVertex[u] = true;
            coveredVertex[v] = true;
            for (int j = i; j < edges.length; j++) {
                if (edges[j][0] == u || edges[j][1] == u || edges[j][0] == v || edges[j][1] == v) {
                    edgeCovered[j] = true;
                }
            }
        }
        return coveredVertex;
    }

    public static List<Integer> selectedVertices(boolean[] cover) {
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < cover.length; i++) {
            if (cover[i]) selected.add(i);
        }
        return selected;
    }
}
