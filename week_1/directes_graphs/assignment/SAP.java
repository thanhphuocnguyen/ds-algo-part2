import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
  private final Digraph G;
  private BreadthFirstDirectedPaths pV, pW;
  private int w, v;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    if (G == null)
      throw new IllegalArgumentException();
    this.G = G;
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    checkBound(v, w);
    if (v != this.v || w != this.w) {
      pV = new BreadthFirstDirectedPaths(G, v);
      pW = new BreadthFirstDirectedPaths(G, w);
    }
    this.v = v;
    this.w = w;
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < G.V(); i++) {
      if (pV.hasPathTo(i) && pW.hasPathTo(i))
        minDist = Math.min(minDist, pV.distTo(i) + pW.distTo(i));
    }

    return minDist == Integer.MAX_VALUE ? -1 : minDist;
  }

  private void checkBound(int v, int w) {
    if (v > G.V() || w > G.V() || v < 0 || w < 0)
      throw new IllegalArgumentException();
  }

  // a common ancestor of v and w that participates in a shortest ancestral path;
  // -1 if no such path
  public int ancestor(int v, int w) {
    checkBound(v, w);
    if (v != this.v || w != this.w) {
      pV = new BreadthFirstDirectedPaths(G, v);
      pW = new BreadthFirstDirectedPaths(G, w);
    }
    this.v = v;
    this.w = w;
    int minDist = Integer.MAX_VALUE;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (pV.hasPathTo(i) && pW.hasPathTo(i)) {
        int dist = pV.distTo(i) + pW.distTo(i);
        if (dist < minDist) {
          minDist = dist;
          ancestor = i;
        }
      }
    }
    return ancestor;
  }

  private void checkBounds(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null)
      throw new IllegalArgumentException();
    for (int i : v) {
      if (i > G.V() || i < 0)
        throw new IllegalArgumentException();
    }
    for (int i : w) {
      if (i > G.V() || i < 0)
        throw new IllegalArgumentException();
    }
  }

  // length of shortest ancestral path between any vertex in v and any vertex in
  // w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    checkBounds(v, w);
    pV = new BreadthFirstDirectedPaths(G, v);
    pW = new BreadthFirstDirectedPaths(G, w);
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < G.V(); i++) {
      if (pV.hasPathTo(i) && pW.hasPathTo(i))
        minDist = Math.min(minDist, pV.distTo(i) + pW.distTo(i));
    }
    return minDist == Integer.MAX_VALUE ? -1 : minDist;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such
  // path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    checkBounds(v, w);
    pV = new BreadthFirstDirectedPaths(G, v);
    pW = new BreadthFirstDirectedPaths(G, w);
    int minDist = Integer.MAX_VALUE;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (pV.hasPathTo(i) && pW.hasPathTo(i)) {
        int dist = pV.distTo(i) + pW.distTo(i);
        if (dist < minDist) {
          minDist = dist;
          ancestor = i;
        }
      }
    }
    return ancestor;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
