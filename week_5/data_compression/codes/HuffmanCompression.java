package codes;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * HuffmanCompression
 */
/**
 * The HuffmanCompression class provides methods for compressing and expanding data using the Huffman coding algorithm.
 * It supports compressing a given input string and expanding a compressed binary file back to its original form.
 */
public class HuffmanCompression {
    private static int R = 256; // extended ASCII

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
    
    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        for (char c : input) {
            freq[c]++;
        }

        Node root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st, root, "");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (char c : input) {
            String code = st[c];
            for (char bit : code.toCharArray()) {
                if (bit == '0')
                    BinaryStdOut.write(false);
                else
                    BinaryStdOut.write(true);
            }
        }
        BinaryStdOut.close();
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    
    public void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();
        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                if (!BinaryStdIn.readBoolean())
                    x = x.left;
                else
                    x = x.right;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            char c = BinaryStdIn.readChar(8);
            return new Node(c, 0, null, null);
        }
        Node x = readTrie();
        Node y = readTrie();
        return new Node('\0', 0, x, y);
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char i = 0; i < R; i++) {
            if (freq[i] > 0) {
                pq.insert(new Node(i, freq[i], null, null));
            }
        }

        while (pq.size() > 1) {
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }
        return pq.delMin();
    }
}