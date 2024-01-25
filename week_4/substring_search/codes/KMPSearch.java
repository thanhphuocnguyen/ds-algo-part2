/**
 * KMPSearch
 */
public class KMPSearch {
    private final int[][] dfa;
    private int R = 256;
    private int M;

    public KMPSearch(String pattern) {
        M = pattern.length();
        dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public int search(String txt) {
        int N = txt.length();
        int i = 0;
        for (int j = 0; j < N; j++) {
            i = dfa[txt.charAt(j)][i];
            if (i == M)
                return j - M + 1;
        }
        return N;
    }
}