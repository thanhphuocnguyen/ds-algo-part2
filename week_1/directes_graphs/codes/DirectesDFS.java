public class DirectesDFS {
  private boolean[] marked;

  // You can compute to find the futhurest node from s
  // DFS of digraph is exactly the same as DFS of undirected graph
  // S is start, graph is Digraph
  public DirectesDFS(DiGraph graph, int s) {
    this.marked = new boolean[graph.V()];
    dfs(graph, s);
  }

  private void dfs(DiGraph g, int v) {
    marked[v] = true;
    for (int w : g.adj(v)) {
      if (!marked[w]) {
        dfs(g, w);
      }
    }
  }

  public boolean visited(int v) {
    return marked[v];
  }
}
