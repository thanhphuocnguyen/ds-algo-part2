/**
 * MSD (Most Significant Digit) sort is a stable sort that sorts strings
 */
public class MSD {

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo) {
            return;
        }
        int[] count = new int[Character.MAX_VALUE + 2];
        for (int i = lo; i < hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int r = 0; r < Character.MAX_VALUE + 1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i < hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }
        for (int i = lo; i < hi; i++) {
            a[i] = aux[i - lo];
        }
        for (int r = 0; r < Character.MAX_VALUE + 1; r++) {
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }
}