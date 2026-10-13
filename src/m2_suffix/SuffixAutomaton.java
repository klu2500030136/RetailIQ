package m2_suffix;

import java.util.HashMap;
import java.util.Map;

public class SuffixAutomaton {
    
    public static class State {
        int length;
        int link;
        Map<Character, Integer> next = new HashMap<>();
    }

    private State[] st;
    private int sz;
    private int last;
    private int maxStates;

    public SuffixAutomaton(String text) {
        maxStates = text.length() * 2;
        st = new State[maxStates];
        for (int i = 0; i < maxStates; i++) {
            st[i] = new State();
        }
        
        st[0].length = 0;
        st[0].link = -1;
        sz = 1;
        last = 0;
        
        for (char c : text.toCharArray()) {
            extend(c);
        }
    }

    private void extend(char c) {
        int cur = sz++;
        st[cur].length = st[last].length + 1;
        int p = last;
        
        while (p != -1 && !st[p].next.containsKey(c)) {
            st[p].next.put(c, cur);
            p = st[p].link;
        }
        
        if (p == -1) {
            st[cur].link = 0;
        } else {
            int q = st[p].next.get(c);
            if (st[p].length + 1 == st[q].length) {
                st[cur].link = q;
            } else {
                int clone = sz++;
                st[clone].length = st[p].length + 1;
                st[clone].next.putAll(st[q].next);
                st[clone].link = st[q].link;
                
                while (p != -1 && st[p].next.containsKey(c) && st[p].next.get(c) == q) {
                    st[p].next.put(c, clone);
                    p = st[p].link;
                }
                st[q].link = st[cur].link = clone;
            }
        }
        last = cur;
    }

    public boolean contains(String pattern) {
        if (pattern == null || pattern.isEmpty()) return true;
        int cur = 0;
        for (char c : pattern.toCharArray()) {
            if (!st[cur].next.containsKey(c)) {
                return false;
            }
            cur = st[cur].next.get(c);
        }
        return true;
    }
    
    public int getStateCount() {
        return sz;
    }
}
