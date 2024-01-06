// Shortest Path Tree

import edu.princeton.cs.algs4.Stack;

public class SPT {
  private int[] edgeTo;
  private final EdgeWeightedGraph G;
  private final int s;
  private double[] distTo;

  public SPT(EdgeWeightedGraph G, int s) {
    this.G = G;
    this.s = s;
  }

  public double distTo(int v) {
    return distTo[v];
  }

  public boolean hasPathTo(int v) {
    return distTo[v] < Double.POSITIVE_INFINITY;
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v))
      return null;
    Stack<Integer> path = new Stack<Integer>();
    for (int x = v; x != s; x = edgeTo[x]) {
      path.push(x);
    }
    path.push(s);
    return path;
  }
}
