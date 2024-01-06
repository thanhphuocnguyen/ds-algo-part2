public class HamiltonianPathInDAG {
  private boolean hasHalmintonianPath = false;
  private Iterable<Integer> halmintonianPath = null;

  public HamiltonianPathInDAG(DiGraph G) {
    DepthFirstOrder dfo = new DepthFirstOrder(G);
    int prevVertext = -1;
    for (int vtx : dfo.reversePost()) {
      if (!G.hasEdge(prevVertext, vtx))
        return;
      prevVertext = vtx;
    }
    halmintonianPath = dfo.reversePost();
    hasHalmintonianPath = true;
  }

  public boolean hasHalmintonianPath() {
    return hasHalmintonianPath;
  }

  public Iterable<Integer> halmintonianPath() {
    return halmintonianPath;
  }
}
