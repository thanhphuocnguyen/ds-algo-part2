import edu.princeton.cs.algs4.Stack;

public class ShortestDirectedCycle {
  private int minCycleLenght = Integer.MAX_VALUE;
  Stack<Integer> cycle;

  public ShortestDirectedCycle(DiGraph G) {
    cycle = new Stack<>();
    for (int w = 0; w < G.V(); w++) {
      DirectesBFS bfs = new DirectesBFS(G.reverse(), w);
      for (int v : G.adj(w)) {
        if (bfs.hasPathTo(v) && (bfs.distTo(v) + 1) < minCycleLenght) {
          minCycleLenght = bfs.distTo(v) + 1;
          Stack<Integer> newCycle = new Stack<>();
          for (int x : bfs.pathTo(v)) {
            newCycle.push(x);
            if (x == v)
              break;
          }
          cycle = newCycle;
        }
      }
    }

  }

  public Stack<Integer> cycle() {
    return cycle;
  }

  public int length() {
    return minCycleLenght;
  }
}
