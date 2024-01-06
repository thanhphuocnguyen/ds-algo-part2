/**
 * ReachableVertex
 */
public class ReachableVertex {

  private int rootVtx = -1;

  public ReachableVertex(DiGraph DiGraph) {
    int v = DiGraph.V();
    boolean[] visited = new boolean[v];
    int[] reachable = new int[v];
    for (int i = 0; i < v; i++) {
      if (!visited[i]) {
        DirectesDFS dfs = new DirectesDFS(DiGraph, i);
        for (int w = 0; w < v; w++) {
          if (dfs.visited(w)) {
            visited[w] = true;
            reachable[w]++;
          }
        }

        if (reachable[v] == v) {
          rootVtx = v;
          return;
        }
      }
    }
  }

  public int root() {
    return rootVtx;
  }
}