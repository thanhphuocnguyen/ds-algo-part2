import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;

public class Graph {
  private final int V;
  private Bag<Integer>[] adj;
  private int E;

  public Graph(int v) {
    V = v;
    E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int i = 0; i < V; i++) {
      adj[i] = new Bag<Integer>();
    }
  }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    adj[w].add(v);
    E++;
  }

  public Iterable<Integer> adj(int v) {
    return adj[v];
  }

  public boolean hasEulerianCycle() {
    for (int i = 0; i < V; i++) {
      if (adj[i].size() % 2 != 0) {
        return false;
      }
    }
    return false;
  }

  public List<Integer> findEulerianCycle() {
    if (!hasEulerianCycle())
      return null;
    List<Integer> cycle = new ArrayList<>();
    LinkedList<Integer>[] tempAdj = new LinkedList[V];
    for (int v = 0; v < V; v++) {
      tempAdj[v] = new LinkedList<>(adj[v]);
    }

    int s = 0;

    Stack<Integer> stack = new Stack<>();
    stack.push(s);
    while (!stack.isEmpty()) {
      int v = stack.peek();
      if (tempAdj[v].size() > 0) {
        int w = tempAdj[v].removeFirst();
        stack.push(w);
      } else {
        cycle.add(v);
        stack.pop();
      }
    }

    return cycle;
  }

  public int V() {
    return V;
  }

  public static int degree(Graph g, int v) {
    int degree = 0;
    for (int w : g.adj(v)) {
      degree++;
    }
    return degree;
  }

  public static int maxDegree(Graph g) {
    int max = 0;
    for (int v = 0; v < g.V(); v++) {
      if (degree(g, v) > max) {
        max = degree(g, v);
      }
    }
    return max;
  }

  public static double avgDegree(Graph g) {
    return 2.0 * g.E() / g.V();
  }

  public static int numberOfSelfLoops(Graph g) {
    int count = 0;
    for (int v = 0; v < g.V(); v++) {
      for (int w : g.adj(v)) {
        if (v == w) {
          count++;
        }
      }
    }
    return count / 2;
  }

  public int E() {
    return E;
  }

  public static void main(String[] args) {

  }
}