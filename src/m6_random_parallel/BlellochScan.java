package m6_random_parallel;

public class BlellochScan {
    public static int[] exclusivePrefixSum(int[] values) {
        int n = 1;
        while (n < values.length) n <<= 1;
        int[] tree = new int[n];
        for (int i = 0; i < values.length; i++) tree[i] = values[i];

        for (int step = 1; step < n; step <<= 1) {
            for (int i = 0; i < n; i += step << 1) {
                tree[i + (step << 1) - 1] += tree[i + step - 1];
            }
        }
        if (n > 0) tree[n - 1] = 0;
        for (int step = n >> 1; step >= 1; step >>= 1) {
            for (int i = 0; i < n; i += step << 1) {
                int left = i + step - 1;
                int right = i + (step << 1) - 1;
                int temp = tree[left];
                tree[left] = tree[right];
                tree[right] += temp;
            }
        }

        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) result[i] = tree[i];
        return result;
    }

    public static int[] inclusivePrefixSum(int[] values) {
        int[] exclusive = exclusivePrefixSum(values);
        int[] inclusive = new int[values.length];
        for (int i = 0; i < values.length; i++) inclusive[i] = exclusive[i] + values[i];
        return inclusive;
    }
}
