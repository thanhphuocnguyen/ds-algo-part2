import java.util.Stack;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * DijikstraSP
 */
public class DijkstraSP {

  private double[] distTo;
  private DirectedEdge[] edgeTo;
  private IndexMinPQ<Double> pq;

  public DijkstraSP(EdgeWeightedGraph G, int s) {
    this.edgeTo = new DirectedEdge[G.V()];
    this.distTo = new double[G.V()];
    pq = new IndexMinPQ<>(G.V());
    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }

    distTo[s] = 0.0;
    pq.insert(s, 0.0);
    while (!pq.isEmpty()) {
      int v = pq.delMin();
      for (DirectedEdge e : G.adj(v)) {
        relax(e);
      }
    }
  }

  public void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] >= distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      if (pq.contains(w)) {
        pq.decreaseKey(w, distTo[w]);
      } else {
        pq.insert(w, distTo[w]);
      }
    }
  }

  private boolean hasPathTo(int v) {
    return distTo[v] < Double.POSITIVE_INFINITY;
  }

  public Iterable<DirectedEdge> pathTo(int v) {
    if (!hasPathTo(v)) {
      return null;
    }
    Stack<DirectedEdge> path = new Stack<>();
    for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
      path.push(e);
    }
    return path;
  }
}