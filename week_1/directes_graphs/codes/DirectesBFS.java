import java.util.Stack;

import edu.princeton.cs.algs4.Queue;

public class DirectesBFS {
  private boolean[] marked;
  private int[] distTo;

  public DirectesBFS(DiGraph graph, int s) {
    this.marked = new boolean[graph.V()];
    this.distTo = new int[graph.V()];
    bfs(graph, s);
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) {
      return null;
    }
    Stack<Integer> path = new Stack<>();
    for (int x = v; distTo[x] != 0; x = distTo[x]) {
      path.push(x);
    }
    path.push(v);
    return path;
  }

  public int distTo(int v) {
    return distTo[v];
  }

  private void bfs(DiGraph graph, int v) {
    Queue<Integer> queue = new Queue<>();
    queue.enqueue(v);
    marked[v] = true;
    distTo[v] = 0;
    while (!queue.isEmpty()) {
      int w = queue.dequeue();
      for (int x : graph.adj(w)) {
        if (!marked[x]) {
          queue.enqueue(x);
          marked[x] = true;
          distTo[x] = distTo[w] + 1;
        }
      }
    }
  }
}
