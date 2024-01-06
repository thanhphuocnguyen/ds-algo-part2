import edu.princeton.cs.algs4.Stack;

public class Topological {
  private boolean[] marked;
  private Stack<Integer> reversePost;

  public Topological(EdgeWeightedGraph G) {
    marked = new boolean[G.V()];
    reversePost = new Stack<>();
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
      }
    }
  }

  private void dfs(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (DirectedEdge w : G.adj(v)) {
      if (!marked[w.to()]) {
        dfs(G, w.to());
      }
    }
    reversePost.push(v);
  }

  public Iterable<Integer> order() {
    return reversePost;
  }
}
