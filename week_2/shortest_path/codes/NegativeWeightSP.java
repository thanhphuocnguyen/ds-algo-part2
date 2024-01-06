public class NegativeWeightSP {
  private double[] distTo;
  private DirectedEdge[] edgeTo;

  public NegativeWeightSP(EdgeWeightedGraph G, int s) {
    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    for (int i = 0; i < G.V(); i++) {
      for (int v = 0; v < G.V(); v++) {
        for (DirectedEdge e : G.adj(v)) {
          relax(e);
        }
      }
    }
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] > distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
    }
  }
}
