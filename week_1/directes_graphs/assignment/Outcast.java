import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
  private final WordNet wn;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
    this.wn = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns) {
    int max_dist = -1;
    int ans = -1;
    int[] dist = new int[nouns.length];
    for (int i = 0; i < nouns.length; i++) {
      for (int j = i + 1; j < nouns.length; j++) {
        int d = wn.distance(nouns[i], nouns[j]);
        dist[i] += d;
        dist[j] += d;
      }
      if (dist[i] > max_dist) {
        max_dist = dist[i];
        ans = i;
      }
    }
    return nouns[ans];
  }

  // see test client below
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
