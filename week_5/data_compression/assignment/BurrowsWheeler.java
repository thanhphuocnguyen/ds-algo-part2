import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256; // extended ASCII
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output

    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        for (int i = 0; i < n; i++) {
            if (csa.index(i) == 0) {
                // Write row number where original string ends up
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            // Get index of last character
            int idx = (csa.index(i) + n - 1) % n;
            // Write last character of suffix
            BinaryStdOut.write(s.charAt(idx), 8);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int n = s.length();
        // Initialize next array
        int[] next = new int[n];
        // Initialize count array
        int[] count = new int[R + 1];
        for (int i = 0; i < n; i++) {
            // Count frequency of each character
            count[s.charAt(i) + 1]++;
        }
        for (int i = 0; i < R; i++) {
            // Compute cumulates
            count[i + 1] += count[i];
        }
        for (int i = 0; i < n; i++) {
            // Compute next array
            next[count[s.charAt(i)]++] = i;
        }
        for (int i = next[first], c = 0; c < n; i = next[i], c++) {
            // Write original string
            BinaryStdOut.write(s.charAt(i), 8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: java BurrowsWheeler <- or +>");
        }
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        } else {
            throw new IllegalArgumentException("Usage: java BurrowsWheeler <- or +>");
        }
    }

}
