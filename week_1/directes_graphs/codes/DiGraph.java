import edu.princeton.cs.algs4.Bag;

/**
 * DiGraph
 */
public class DiGraph {

  private final int V;
  private int E;
  private final Bag<Integer>[] adj;

  public DiGraph(int V) {
    this.V = V;
    this.E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<Integer>();
    }
  }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    E++;
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  public Iterable<Integer> adj(int v) {
    return adj[v];
  }

  // reverse
  public DiGraph reverse() {
    DiGraph g = new DiGraph(V);
    for (int v = 0; v < V; v++) {
      for (int w : adj[v]) {
        g.addEdge(w, v);
      }
    }
    return g;
  }

  public boolean hasEdge(int v, int w) {
    for (int x : adj[v]) {
      if (x == w) {
        return true;
      }
    }
    return false;
  }
}