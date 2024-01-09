/**
 * LSD (Least Significant Digit) sort is a stable sort that sorts strings
 */
public class LSD {

    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256; // number of characters in the alphabet
        String[] aux = new String[N];
        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1]; // Compute frequency counts.
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }
            for (int r = 0; r < R; r++) { // Transform counts to indices.
                count[r + 1] += count[r];
            }
            for (int i = 0; i < N; i++) { // Distribute.
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < N; i++) { // Copy back.
                a[i] = aux[i];
            }
        }
    }
}