import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    private final TrieSET dictionarySet;
    private int[] scoreSet;

    public BoggleSolver(String[] dictionary) {
        dictionarySet = new TrieSET();
        for (String word : dictionary) {
            dictionarySet.add(word);
        }
        scoreSet = new int[] { 0, 0, 0, 1, 1, 2, 3, 5, 11 };
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        TrieSET validWords = new TrieSET();
        int rows = board.rows();
        int cols = board.cols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] visited = new boolean[rows][cols];
                StringBuilder currentWord = new StringBuilder();
                explore(board, i, j, visited, currentWord, validWords);
            }
        }

        return validWords;
    }

    private void explore(BoggleBoard board, int i, int j, boolean[][] visited, StringBuilder currentWord,
            TrieSET validWords) {
        if (i < 0 || i >= board.rows() || j < 0 || j >= board.cols() || visited[i][j]) {
            return;
        }

        char letter = board.getLetter(i, j);
        if (letter == 'Q') {
            currentWord.append("QU");
        } else {
            currentWord.append(letter);
        }

        visited[i][j] = true;

        if (currentWord.length() >= 3 && dictionarySet.contains(currentWord.toString())) {
            validWords.add(currentWord.toString());
        }

        if (dictionarySet.longestPrefixOf(currentWord.toString()) != null) {
            for (int row = i - 1; row <= i + 1; row++) {
                for (int col = j - 1; col <= j + 1; col++) {
                    explore(board, row, col, visited, currentWord, validWords);
                }
            }
        }

        visited[i][j] = false;
        int length = currentWord.length();
        if (length >= 2 && currentWord.charAt(length - 1) == 'U' && currentWord.charAt(length - 2) == 'Q') {
            currentWord.deleteCharAt(length - 1);
            currentWord.deleteCharAt(length - 2);
        } else {
            currentWord.deleteCharAt(length - 1);
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionarySet.contains(word)) {
            return word.length() <= scoreSet.length ? scoreSet[word.length()] : 11;
        }
        return 0;
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
