package m2_suffix;

public class M2Demo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("M2 SUFFIX STRUCTURE BENCHMARK");
        System.out.println("========================================");

        String text = "mississippi";
        // To make a longer string for benchmarking
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(text);
        }
        String largeText = sb.toString();
        
        System.out.println("Benchmarking with text length: " + largeText.length());

        long t1 = System.nanoTime();
        SuffixArray sa = new SuffixArray(largeText);
        long t2 = System.nanoTime();
        System.out.println("Suffix Array Construction : " + (t2 - t1) / 1000000.0 + " ms");

        t1 = System.nanoTime();
        KasaiLCP.buildLCPArray(largeText, sa.getSuffixArray());
        t2 = System.nanoTime();
        System.out.println("Kasai LCP Construction    : " + (t2 - t1) / 1000000.0 + " ms");

        t1 = System.nanoTime();
        SAIS.build(largeText);
        t2 = System.nanoTime();
        System.out.println("SA-IS Construction        : " + (t2 - t1) / 1000000.0 + " ms");

        t1 = System.nanoTime();
        SuffixAutomaton sam = new SuffixAutomaton(largeText);
        t2 = System.nanoTime();
        System.out.println("Suffix Automaton Build    : " + (t2 - t1) / 1000000.0 + " ms");

        // We use a shorter text for naive suffix tree because O(n^2) gets slow
        String smallText = largeText.substring(0, Math.min(1000, largeText.length()));
        t1 = System.nanoTime();
        SuffixTree st = new SuffixTree(smallText);
        t2 = System.nanoTime();
        System.out.println("Suffix Tree Build (Naive) : " + (t2 - t1) / 1000000.0 + " ms (length " + smallText.length() + ")");

        System.out.println("\nQueries:");
        String query = "ississ";
        
        t1 = System.nanoTime();
        sa.search(query);
        t2 = System.nanoTime();
        System.out.println("Suffix Array Query        : " + (t2 - t1) / 1000000.0 + " ms");

        t1 = System.nanoTime();
        sam.contains(query);
        t2 = System.nanoTime();
        System.out.println("Suffix Automaton Query    : " + (t2 - t1) / 1000000.0 + " ms");

        t1 = System.nanoTime();
        st.contains(query);
        t2 = System.nanoTime();
        System.out.println("Suffix Tree Query         : " + (t2 - t1) / 1000000.0 + " ms");
    }
}
