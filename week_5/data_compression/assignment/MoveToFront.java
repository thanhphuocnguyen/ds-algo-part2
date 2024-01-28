package assignment;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256; // extended ASCII
    // apply move-to-front encoding, reading from standard input and writing to
    // standard output

    public static void encode() {
        char[] sequence = initializeSequence();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = 0;
            while (sequence[index] != c) {
                index++;
            }
            BinaryStdOut.write(index, 8);
            moveToFront(sequence, index);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to
    // standard output
    public static void decode() {
        char[] sequence = initializeSequence();
        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readChar();
            BinaryStdOut.write(sequence[index], 8);
            moveToFront(sequence, index);
        }
        BinaryStdOut.close();
    }

    private static char[] initializeSequence() {
        char[] sequence = new char[R];
        for (char i = 0; i < R; i++) {
            sequence[i] = i;
        }
        return sequence;
    }

    private static void moveToFront(char[] sequence, int index) {
        char c = sequence[index];
        System.arraycopy(sequence, 0, sequence, 1, index);
        sequence[0] = c;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Please provide an argument");

        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
        else
            throw new IllegalArgumentException("Argument must be either + or -");
    }
}
