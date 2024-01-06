import edu.princeton.cs.algs4.IndexMinPQ;

public class MonotonicShortestPath {
  private double[] distTo;
  private DirectedEdge[] edgeTo;
  private boolean increasing;
  IndexMinPQ<Double> pq;

  public MonotonicShortestPath(EdgeWeightedGraph G, int s, boolean increasing) {
    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    this.increasing = increasing;
    pq = new IndexMinPQ(G.V());
    for (int i = 0; i < G.V(); i++) {
      distTo[i] = Double.POSITIVE_INFINITY;
    }
    pq.insert(s, 0.0);
    while (!pq.isEmpty()) {
      int v = pq.delMin();
      for (DirectedEdge e : G.adj(v)) {
        relax(e);
      }
    }
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    double weight = increasing ? e.weight() : -e.weight();
    if (distTo[w] >= distTo[v] + weight) {
      distTo[w] = distTo[v] + weight;
      edgeTo[w] = e;
      if (pq.contains(w)) {
        pq.insert(w, distTo[w]);
      } else {
        pq.insert(w, distTo[w]);
      }
    }
  }
}
