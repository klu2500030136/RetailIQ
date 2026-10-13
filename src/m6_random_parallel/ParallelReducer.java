package m6_random_parallel;

public class ParallelReducer {
    private static final int PARALLEL_THRESHOLD = 4096;

    public static int sum(int[] values, int threadCount) {
        if (values.length == 0) return 0;
        if (values.length < PARALLEL_THRESHOLD || threadCount <= 1) {
            int total = 0;
            for (int value : values) total += value;
            return total;
        }
        int workers = Math.max(1, Math.min(threadCount, values.length));
        int[] partial = new int[workers];
        Thread[] threads = new Thread[workers];
        for (int t = 0; t < workers; t++) {
            final int worker = t;
            threads[t] = new Thread(new Runnable() {
                public void run() {
                    int start = worker * values.length / workers;
                    int end = (worker + 1) * values.length / workers;
                    int local = 0;
                    for (int i = start; i < end; i++) local += values[i];
                    partial[worker] = local;
                }
            });
            threads[t].start();
        }
        joinAll(threads);
        int total = 0;
        for (int p : partial) total += p;
        return total;
    }

    public static int max(int[] values, int threadCount) {
        if (values.length == 0) throw new IllegalArgumentException("Cannot reduce empty array");
        if (values.length < PARALLEL_THRESHOLD || threadCount <= 1) {
            int best = values[0];
            for (int i = 1; i < values.length; i++) {
                if (values[i] > best) best = values[i];
            }
            return best;
        }
        int workers = Math.max(1, Math.min(threadCount, values.length));
        int[] partial = new int[workers];
        Thread[] threads = new Thread[workers];
        for (int t = 0; t < workers; t++) {
            final int worker = t;
            threads[t] = new Thread(new Runnable() {
                public void run() {
                    int start = worker * values.length / workers;
                    int end = (worker + 1) * values.length / workers;
                    int local = values[start];
                    for (int i = start + 1; i < end; i++) {
                        if (values[i] > local) local = values[i];
                    }
                    partial[worker] = local;
                }
            });
            threads[t].start();
        }
        joinAll(threads);
        int best = partial[0];
        for (int i = 1; i < partial.length; i++) {
            if (partial[i] > best) best = partial[i];
        }
        return best;
    }

    private static void joinAll(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Parallel reduction interrupted", e);
            }
        }
    }
}
