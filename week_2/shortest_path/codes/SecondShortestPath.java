import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class SecondShortestPath {
  private double[] distTo;
  private DirectedEdge[] edgeTo;
  IndexMinPQ<Double> pq;

  public SecondShortestPath(EdgeWeightedDigraph G, int s, int t) {
    DijkstraSP originalSP = new DijkstraSP(G, s);
    DirectedEdge criticalEdge = null;
    double maxDistanceChange = Integer.MIN_VALUE;
    for (DirectedEdge edgeToRemove : originalSP.pathTo(t)) {
      EdgeWeightedDigraph modifiedGraph = graphAfterRemoveEdge(G, edgeToRemove);
      DijkstraSP modifiedSP = new DijkstraSP(modifiedGraph, s);
      double distanceChange = modifiedSP.distTo(t) - originalSP.distTo(t);
      if (distanceChange > maxDistanceChange) {
        criticalEdge = edgeToRemove;
        maxDistanceChange = distanceChange;
      }
      if (modifiedSP.distTo(t) == Double.POSITIVE_INFINITY) {
        break;
      }
    }
  }

  private EdgeWeightedDigraph graphAfterRemoveEdge(EdgeWeightedDigraph graph, DirectedEdge edge) {
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(graph.V());
    for (DirectedEdge e : graph.edges()) {
      if (!e.equals(edge)) {
        G.addEdge(e);
      }
    }
    return G;
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] > distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      if (pq.contains(w)) {
        pq.decreaseKey(w, distTo[w]);
      } else {
        pq.insert(w, distTo[w]);
      }
    }
  }
}
