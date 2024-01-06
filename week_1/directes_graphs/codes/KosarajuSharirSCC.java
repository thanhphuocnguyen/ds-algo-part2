/**
 * KosarajuSharirCC
 */
public class KosarajuSharirSCC {
  private boolean[] marked;
  private int[] id;
  private int count;

  public KosarajuSharirSCC(DiGraph G) {
    marked = new boolean[G.V()];
    id = new int[G.V()];
    DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
    for (int v : dfs.reversePost()) {
      if (!marked[v]) {
        dfs(G, v);
        count++;
      }
    }
  }

  private void dfs(DiGraph G, int v) {
    marked[v] = true;
    id[v] = count;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
  }

  public boolean stronglyConnected(int v, int w) {
    return id[v] == id[w];
  }

  public int id(int v) {
    return id[v];
  }

  public int count() {
    return count;
  }
}