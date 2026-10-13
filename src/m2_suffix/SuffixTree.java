package m2_suffix;

import java.util.HashMap;
import java.util.Map;

public class SuffixTree {

    private static class Node {
        Map<Character, Node> children = new HashMap<>();
    }

    private final Node root;

    public SuffixTree(String text) {
        root = new Node();
        // Naive O(n^2) construction
        for (int i = 0; i < text.length(); i++) {
            insertSuffix(text.substring(i));
        }
    }

    private void insertSuffix(String suffix) {
        Node current = root;
        for (char c : suffix.toCharArray()) {
            current.children.putIfAbsent(c, new Node());
            current = current.children.get(c);
        }
    }

    public boolean contains(String pattern) {
        if (pattern == null || pattern.isEmpty()) return true;
        Node current = root;
        for (char c : pattern.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return true;
    }
}
