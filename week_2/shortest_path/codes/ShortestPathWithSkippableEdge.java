import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stack;

public class ShortestPathWithSkippableEdge {
  public Iterable<DirectedEdge> skippablePath(EdgeWeightedDigraph G, int s, int t) {
    DijkstraSP spaths = new DijkstraSP(G, s);
    DijkstraSP tpaths = new DijkstraSP(G, t);

    double min = Double.MAX_VALUE;
    DirectedEdge skippable = null;

    for (DirectedEdge e : G.edges()) {
      int v = e.from();
      int w = e.to();
      if (spaths.distTo(v) + tpaths.distTo(w) < min) {
        skippable = e;
        min = spaths.distTo(v) + tpaths.distTo(w);
      }
    }

    Stack<DirectedEdge> skippablepath = new Stack<DirectedEdge>();
    Stack<DirectedEdge> tmp = new Stack<DirectedEdge>();

    for (DirectedEdge e : tpaths.pathTo(skippable.to()))
      skippablepath.push(e);
    skippablepath.push(skippable);
    for (DirectedEdge e : spaths.pathTo(skippable.from()))
      tmp.push(e);
    for (DirectedEdge e : tmp)
      skippablepath.push(e);
    return skippablepath;
  }

}
