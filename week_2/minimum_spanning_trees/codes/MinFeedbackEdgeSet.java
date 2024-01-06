import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.PrimMST;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;

public class MinFeedbackEdgeSet {

  private EdgeWeightedGraph G;
  private Bag<Edge> feedbackEdgeSet;
  private boolean[] marked;

  public MinFeedbackEdgeSet(EdgeWeightedGraph G) {
    this.G = G;
    feedbackEdgeSet = new Bag<>();
    marked = new boolean[G.V()];
    findMinimumFeedbackEdgeSet();
  }

  private void findMinimumFeedbackEdgeSet() {
    PrimMST mst = new PrimMST(G);
    for (Edge edge : G.edges()) {
      int u = edge.either();
      int v = edge.other(u);
      for (Edge mstEdge : mst.edges()) {
        if (mstEdge.either() != u && mstEdge.other(u) != v) {
          findLightEdgesInCycle(u, v, edge.weight());
        }
      }
    }
  }

  private void findLightEdgesInCycle(int u, int v, double weight) {
    marked[v] = true;
    for (Edge edge : G.adj(v)) {
      int neighbor = edge.other(v);
      if (neighbor == u && edge.weight() == weight) {
        continue;
      }
      if (!marked[neighbor] && edge.weight() <= weight) {
        feedbackEdgeSet.add(edge);
        findLightEdgesInCycle(v, neighbor, edge.weight());
      }
    }
  }

  public Iterable<Edge> getMinFeedbackEdgeSet() {
    return feedbackEdgeSet;
  }

}