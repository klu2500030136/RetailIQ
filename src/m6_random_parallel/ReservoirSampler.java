package m6_random_parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservoirSampler<T> {
    public List<T> sample(List<T> stream, int sampleSize, long seed) {
        if (sampleSize < 0) throw new IllegalArgumentException("Sample size cannot be negative");
        List<T> reservoir = new ArrayList<>();
        Random random = new Random(seed);
        for (int i = 0; i < stream.size(); i++) {
            T item = stream.get(i);
            if (i < sampleSize) {
                reservoir.add(item);
            } else {
                int j = random.nextInt(i + 1);
                if (j < sampleSize) reservoir.set(j, item);
            }
        }
        return reservoir;
    }
}
