public class CircularSuffixArray {
    // circular suffix array of s
    private final int[] index;

    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException();
        index = new int[s.length()];
        Suffix[] suffixes = new Suffix[s.length()];
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = new Suffix(s, i);
        }
        java.util.Arrays.sort(suffixes);
        for (int i = 0; i < s.length(); i++) {
            index[i] = suffixes[i].index;
        }
    }

    // length of s
    public int length() {
        return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IllegalArgumentException();
        return index[i];
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String s;
        private final int index;

        private Suffix(String s, int index) {
            this.s = s;
            this.index = index;
        }

        /*
         * Method charAt(int i): This method returns the character at the ith position
         * of the suffix.
         * It uses the modulo operator % to handle the circular nature of the suffix.
         * If i plus index is greater than the length of the string, it wraps around to
         * the beginning of the string
         */
        private char charAt(int i) {
            return s.charAt((i + index) % s.length());
        }

        @Override
        public int compareTo(Suffix that) {
            if (this == that)
                return 0;
            for (int i = 0; i < s.length(); i++) {
                if (this.charAt(i) < that.charAt(i))
                    return -1;
                else if (this.charAt(i) > that.charAt(i))
                    return 1;
            }
            return 0;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println("Length of s: " + csa.length());
        System.out.println("Index of 0th sorted suffix: " + csa.index(0));
        System.out.println("Index of 1st sorted suffix: " + csa.index(1));
        System.out.println("Index of 2nd sorted suffix: " + csa.index(2));
        System.out.println("Index of 3rd sorted suffix: " + csa.index(3));
        System.out.println("Index of 4th sorted suffix: " + csa.index(4));
        System.out.println("Index of 5th sorted suffix: " + csa.index(5));
        System.out.println("Index of 6th sorted suffix: " + csa.index(6));
        System.out.println("Index of 7th sorted suffix: " + csa.index(7));
        System.out.println("Index of 8th sorted suffix: " + csa.index(8));
        System.out.println("Index of 9th sorted suffix: " + csa.index(9));
        System.out.println("Index of 10th sorted suffix: " + csa.index(10));
        System.out.println("Index of 11th sorted suffix: " + csa.index(11));
        System.out.println("Index of 12th sorted suffix: " + csa.index(12));
    }
}
