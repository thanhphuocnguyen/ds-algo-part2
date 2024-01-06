import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
  private HashMap<String, ArrayList<Integer>> nounSet;
  private HashMap<Integer, String> synset;
  private Digraph hypernym;
  private final SAP sap;
  private int numSynsets;

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException();
    }
    readSynsets(synsets);
    readHypernyms(hypernyms);
    int numberOfRoot = 0;
    for (int i = 0; i < hypernym.V(); i++)
      if (hypernym.outdegree(i) == 0)
        numberOfRoot++;
    if (numberOfRoot != 1)
      throw new IllegalArgumentException("The input does not correspond to a rooted DAG.");
    sap = new SAP(hypernym);
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return this.nounSet.keySet();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }
    return this.nounSet.containsKey(word);
  }

  private void readSynsets(String synsets) {
    synset = new HashMap<>();
    nounSet = new HashMap<>();
    In in = new In(synsets);
    while (!in.isEmpty()) {
      String listSynsets = in.readLine();
      String[] fields = listSynsets.split(",");
      String[] nouns = fields[1].split(" ");
      int nounId = Integer.parseInt(fields[0]);
      for (int i = 0; i < nouns.length; i++) {
        ArrayList<Integer> ids = nounSet.getOrDefault(nouns[i], new ArrayList<>());
        ids.add(nounId);
        nounSet.put(nouns[i], ids);
      }
      synset.put(nounId, fields[1]);
      numSynsets++;
    }
  }

  private void readHypernyms(String hypernyms) {
    hypernym = new Digraph(numSynsets);
    In in = new In(hypernyms);
    while (!in.isEmpty()) {
      String[] fields = in.readLine().split(",");
      int numFields = fields.length;
      for (int i = 1; i < numFields; i++) {
        hypernym.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
      }
    }
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    if (nounA == null || nounB == null) {
      throw new IllegalArgumentException();
    }
    Iterable<Integer> idA = nounSet.get(nounA);
    Iterable<Integer> idB = nounSet.get(nounB);
    return sap.length(idA, idB);
  }

  // a synset (second field of synsets.txt) that is the common ancestor of
  // nounA
  // and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("Not a WordNet noun or is Null");
    }
    Iterable<Integer> idA = nounSet.get(nounA);
    Iterable<Integer> idB = nounSet.get(nounB);
    return synset.get(sap.ancestor(idA, idB));
  }

  // do unit testing of this class
  public static void main(String[] args) {
  }
}