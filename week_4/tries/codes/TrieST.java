package codes;

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
}