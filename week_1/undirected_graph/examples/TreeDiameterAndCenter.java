/**
 * TreeDiameterAndCenter
 */
public class TreeDiameterAndCenter {

  private int diameter = -1;
  private int center = -1;

  public TreeDiameterAndCenter(Graph g) {
    int root = 0;
    dfs(g, root, root, 0);
    diameter = -1;
    dfs(g, center, center, 0);
  }

  public int diameter() {
    return diameter;
  }

  public int center() {
    return center;
  }

  public void dfs(Graph g, int v, int parent, int depth) {
    if (depth > diameter) {
      diameter = depth;
      center = v;
    }

    for (int w : g.adj(v)) {
      if (w != parent) {
        dfs(g, w, v, depth + 1);
      }
    }
  }
}