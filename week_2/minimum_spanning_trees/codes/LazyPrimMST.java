import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class LazyPrimMST {
  private boolean[] marked;
  private Queue<Edge> mst;
  private MinPQ<Edge> pq;

  public LazyPrimMST(EdgeWeightedGraph G) {
    this.marked = new boolean[G.V()];
    this.mst = new Queue<>();
    this.pq = new MinPQ<>();
    visit(G, 0);
    while (!pq.isEmpty()) {
      Edge e = pq.delMin();
      int v = e.either();
      int w = e.other(v);
      if (marked[v] && marked[w])
        continue;
      mst.enqueue(e);
      if (!marked[v])
        visit(G, v);
      if (!marked[w])
        visit(G, w);
    }
  }

  private void visit(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (Edge e : G.adj(v)) {
      int w = e.other(v);
      if (marked[w])
        continue;
      pq.insert(e);
    }
  }
}
