import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
  private final int V;
  private int E;
  private final Bag<Edge>[] adj;

  public EdgeWeightedGraph(int v) {
    this.V = v;
    adj = (Bag<Edge>[]) new Bag[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new Bag<Edge>();
    }
  }

  public EdgeWeightedGraph(In in) {
    this(in.readInt());
    int E = in.readInt();

    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      double weight = in.readDouble();
      Edge e = new Edge(v, w, weight);
      addEdge(e);
    }
  }

  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public Iterable<Edge> adj(int v) {
    return adj[v];
  }

  public Iterable<Edge> edges() {
    Bag<Edge> b = new Bag<Edge>();
    for (int v = 0; v < V; v++) {
      for (Edge e : adj[v]) {
        b.add(e);
      }
    }
    return b;
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }
}
