package m1_string;

import java.util.*;

public class AhoCorasick {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        TrieNode fail = null;
        List<String> output = new ArrayList<>();
    }

    private final TrieNode root;

    public AhoCorasick() {
        this.root = new TrieNode();
    }

    public void addPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) return;
        TrieNode current = root;
        for (char c : pattern.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.output.add(pattern);
    }

    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        root.fail = root;

        // Initialize queue with level 1 nodes
        for (TrieNode child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();

            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();
                
                TrieNode fallback = current.fail;
                while (fallback != root && !fallback.children.containsKey(c)) {
                    fallback = fallback.fail;
                }
                
                if (fallback.children.containsKey(c)) {
                    child.fail = fallback.children.get(c);
                } else {
                    child.fail = root;
                }
                
                // Add failure link outputs to current node outputs
                child.output.addAll(child.fail.output);
                
                queue.add(child);
            }
        }
    }

    public List<KeywordMatch> search(String text) {
        List<KeywordMatch> matches = new ArrayList<>();
        if (text == null || text.isEmpty()) return matches;

        TrieNode current = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            while (current != root && !current.children.containsKey(c)) {
                current = current.fail;
            }
            
            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            } else {
                current = root;
            }
            
            for (String pattern : current.output) {
                int startIndex = i - pattern.length() + 1;
                matches.add(new KeywordMatch(pattern, startIndex, i));
            }
        }
        return matches;
    }
}
