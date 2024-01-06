import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedGraph {
  private final int V;
  private int E;
  private Bag<DirectedEdge>[] adj;

  public EdgeWeightedGraph(int V) {
    this.V = V;
    this.E = 0;
    adj = (Bag<DirectedEdge>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<DirectedEdge>();
    }
  }

  public void addEdge(DirectedEdge e) {
    int v = e.from();
    adj[v].add(e);
    E++;
  }

  public Iterable<DirectedEdge> adj(int v) {
    return adj[v];
  }

  public Iterable<DirectedEdge> edges() {
    Bag<DirectedEdge> edges = new Bag<DirectedEdge>();
    for (int v = 0; v < V; v++) {
      for (DirectedEdge e : adj[v]) {
        edges.add(e);
      }
    }
    return edges;
  }
  public int V() {
    return V;
  }

  public int E() {
    return E;
  }
}
