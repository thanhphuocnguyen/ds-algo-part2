package codes;

import edu.princeton.cs.algs4.Queue;

/**
 * TrieST
 */
public class TrieST<Value> {

    private static final int R = 256;
    private Node root = new Node();

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    public void put(String key, Object value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, Object value, int d) {
        if (x == null)
            x = new Node();
        if (key.length() == d) {
            x.value = value;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, value, d + 1);
        return x;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;
        return (Value) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d);

        return get(x.next[c], key, d + 1);
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length()) {
            x.value = null;
            return x;
        }
        char c = key.charAt(d); // next character
        x.next[c] = delete(x.next[c], key, d + 1);
        return x;
    }

    // character-based operations
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, "", queue);
        return queue;
    }

    private void collect(Node x, String prefix, Queue<String> queue) {
        if (x == null)
            return;
        if (x.value != null)
            queue.enqueue(prefix);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, queue);
        }
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, prefix, queue);
        return queue;
    }

    public String longestPrefixOf(String query) {
        int length = search(root, query, 0, 0);
        return query.substring(0, length);
    }

    private int search(Node x, String query, int d, int length) {
        if (x == null)
            return length;
        if (x.value != null)
            length = d;
        if (d == query.length())
            return length;
        char c = query.charAt(d);
        return search(x.next[c], query, d + 1, length);
    }
}