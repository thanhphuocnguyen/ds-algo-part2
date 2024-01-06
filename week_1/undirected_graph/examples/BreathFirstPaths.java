import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class BreathFirstPaths {
  private boolean[] marked;
  private int[] edgeTo;
  private int s;
  private int[] distTo;

  public int[] distTo() {
    return distTo;
  }

  public BreathFirstPaths(Graph g, int s) {
    this.s = s;
    marked = new boolean[g.V()];
    edgeTo = new int[g.V()];
    distTo = new int[g.V()];
    bfs(g, s);
  }

  private void bfs(Graph g, int s) {
    Queue<Integer> q = new Queue<>();
    q.enqueue(s);
    marked[s] = true;
    distTo[s] = 0;
    while (!q.isEmpty()) {
      int v = q.dequeue();
      for (int w : g.adj(v)) {
        if (!marked[w]) {
          distTo[w] = distTo[v] + 1;
          edgeTo[w] = v;
          marked[w] = true;
          q.enqueue(w);
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
