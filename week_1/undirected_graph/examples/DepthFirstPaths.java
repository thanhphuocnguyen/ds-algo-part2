import edu.princeton.cs.algs4.Stack;

/**
 * DepthFirstPaths
 */
public class DepthFirstPaths {

  private boolean[] marked;
  private int[] edgeTo;
  private int s;

  public DepthFirstPaths(Graph g, int s) {
    marked = new boolean[g.V()];
    edgeTo = new int[g.V()];
    this.s = s;
    dfs(g, s);
    nonRecursiveDfs(g, s);
  }

  private void dfs(Graph p, int v) {
    marked[v] = true;
    for (int w : p.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
        dfs(p, w);
      }
    }
  }

  private void nonRecursiveDfs(Graph p, int v) {
    Stack<Integer> path = new Stack<>();
    path.push(v);
    while (!path.isEmpty()) {
      int w = path.pop();
      marked[w] = true;
      for (Integer x : p.adj(w)) {
        if (!marked[w]) {
          path.push(x);
        }
      }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v))
      return null;
    Stack<Integer> path = new Stack<>();
    for (int x = v; x != s; x = edgeTo[x]) {
      path.push(x);
    }
    path.push(s);
    return path;
  }
}