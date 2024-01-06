import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

public class KruskalMST {
  Queue<Edge> mst = new Queue<Edge>();
  double minimumBottleneck = Integer.MAX_VALUE;

  public KruskalMST(edu.princeton.cs.algs4.EdgeWeightedGraph G) {
    MinPQ<Edge> pq = new MinPQ();
    for (Edge e : G.edges()) {
      pq.insert(e);
    }

    UF uf = new UF(G.V());
    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either();
      int w = e.other(v);
      if (uf.find(v) != uf.find(w)) {
        uf.union(v, w);
        mst.enqueue(e);
        if (e.weight() < minimumBottleneck) {
          minimumBottleneck = e.weight();
        }
      }
    }
  }

  public double minimumBottleneck() {
    return minimumBottleneck;
  }

  public Iterable<Edge> edges() {
    return mst;
  }
}
