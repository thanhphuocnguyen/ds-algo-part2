import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    private TrieDict dictionarySet;

    public BoggleSolver(String[] dictionary) {
        dictionarySet = new TrieDict();
        for (String word : dictionary) {
            dictionarySet.put(word);
        }
    }

    private static class Node {
        private Object value;
        private int depth = 0;
        private Node[] next;

        private Node(int R) {
            next = new Node[R];
        }
    }

    private static class TrieDict {
        private static final int R = 26;
        private Node root = new Node(R);
        private static final char FST = 'A';

        private void put(String key) {
            root = put(root, key, 0);
        }

        private Node put(Node x, String key, int d) {
            if (x == null) {
                x = new Node(R);
                x.depth = d;
            }
            if (d == key.length()) {
                x.value = lengthToScore(key.length());
                return x;
            }
            char c = key.charAt(d);
            x.next[c - FST] = put(x.next[c - FST], key, d + 1);
            return x;
        }

        private int lengthToScore(int length) {
            if (length <= 2) {
                return 0;
            } else if (length <= 4) {
                return 1;
            } else if (length <= 5) {
                return 2;
            } else if (length <= 6) {
                return 3;
            } else if (length <= 7) {
                return 5;
            } else {
                return 11;
            }
        }

        private int get(String key) {
            return get(root, key, 0);
        }

        private int get(Node x, String key, int d) {
            if (x == null) {
                return -1;
            }
            if (d == key.length()) {
                if (x.value == null) {
                    return 0;
                }
                return (int) x.value;
            }
            char c = key.charAt(d);
            return get(x.next[c - FST], key, d + 1);
        }

        private Node getPrefix(Node cached, String prefix) {
            if (cached == null) {
                return getPrefix(root, prefix, 0);
            } else {
                return getPrefix(cached, prefix, cached.depth);
            }
        }

        private Node getPrefix(Node x, String prefix, int d) {
            if (x == null) {
                return null;
            }
            if (d == prefix.length()) {
                return x;
            }
            char c = prefix.charAt(d);
            return getPrefix(x.next[c - FST], prefix, d + 1);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> validWords = new HashSet<>();
        int rows = board.rows();
        int cols = board.cols();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] visited = new boolean[rows][cols];
                dfs(board, i, j, visited, prefix, validWords, null);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, int row, int col, boolean[][] visited, StringBuilder prefix,
            HashSet<String> validWords, Node cached) {
        if (visited[row][col]) {
            return;
        }
        char letter = board.getLetter(row, col);
        prefix.append(letter == 'Q' ? "QU" : letter);

        visited[row][col] = true;

        if (prefix.length() >= 3 && dictionarySet.getPrefix(cached, prefix.toString()) != null) {
            validWords.add(prefix.toString());
        }

        // StdOut.println(dictionarySet.keysWithPrefix(prefix.toString()));
        cached = dictionarySet.getPrefix(cached, prefix.toString());
        if (cached != null) {
            int rows = board.rows();
            int cols = board.cols();
            for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                    dfs(board, i, j, visited, prefix, validWords, cached);
                }
            }
        }
        prefix.deleteCharAt(prefix.length() - 1);
        if (letter == 'Q') {
            prefix.deleteCharAt(prefix.length() - 1);
        }

        visited[row][col] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null)
            throw new NullPointerException();
        int score = dictionarySet.get(word);
        if (score <= 0)
            return 0;
        else
            return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
