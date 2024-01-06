public class MSTEdgeChecker {
  private boolean isEdgeInMST;
  private boolean[] marked;

  public MSTEdgeChecker(EdgeWeightedGraph G, Edge edgeToCheck) {
    isEdgeInMST = false;
    marked = new boolean[G.V()];
    int u = edgeToCheck.either();
    int v = edgeToCheck.other(u);
    dfs(G, u, v, edgeToCheck.weight());
  }

  private void dfs(EdgeWeightedGraph G, int u, int v, double weight) {
    marked[u] = true;
    for (Edge e : G.adj(u)) {
      int w = e.other(u);
      if (!marked[w] && e.weight() < weight) {
        if (w == v) {
          isEdgeInMST = true;
          return;
        }
        dfs(G, w, v, e.weight());
      }

    }
  }

  public boolean isEdgeInMST() {
    return !isEdgeInMST;
  }
}
