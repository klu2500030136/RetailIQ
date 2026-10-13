package m1_string;

public class M1Demo {
    public static void main(String[] args) {
        String text = "this is a very long string used for benchmarking string search algorithms. we will search for the word benchmarking within this string multiple times to see the performance.";
        String pattern = "benchmarking";
        
        System.out.println("M1 STRING ALGORITHM BENCHMARK");
        System.out.println("Text length: " + text.length());
        System.out.println("Pattern length: " + pattern.length());
        
        // Warmup
        for (int i = 0; i < 10000; i++) {
            NaiveMatcher.search(text, pattern);
            KMPMatcher.search(text, pattern);
            RabinKarpMatcher.search(text, pattern);
        }
        
        int iterations = 100000;
        
        long t1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            NaiveMatcher.search(text, pattern);
        }
        long t2 = System.nanoTime();
        System.out.println("Naive      : " + (t2 - t1) / 1000000.0 + " ms (Total matches: " + NaiveMatcher.search(text, pattern).size() + ")");
        
        t1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            KMPMatcher.search(text, pattern);
        }
        t2 = System.nanoTime();
        System.out.println("KMP        : " + (t2 - t1) / 1000000.0 + " ms (Total matches: " + KMPMatcher.search(text, pattern).size() + ")");
        
        t1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            RabinKarpMatcher.search(text, pattern);
        }
        t2 = System.nanoTime();
        System.out.println("Rabin-Karp : " + (t2 - t1) / 1000000.0 + " ms (Total matches: " + RabinKarpMatcher.search(text, pattern).size() + ")");
    }
}
