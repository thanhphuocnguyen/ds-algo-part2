import edu.princeton.cs.algs4.Stack;

public class DepthFirstOrder {
  private boolean[] marked;
  private Stack<Integer> reversePost;

  public DepthFirstOrder(DiGraph G) {
    reversePost = new Stack<>();
    marked = new boolean[G.V()];
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
      }
    }
  }

  private void dfs(DiGraph g, int v) {
    marked[v] = true;
    for (int w : g.adj(v)) {
      if (!marked[w]) {
        dfs(g, w);
      }
    }
    reversePost.push(v);
  }

  public Iterable<Integer> reversePost() {
    return reversePost;
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }
}
