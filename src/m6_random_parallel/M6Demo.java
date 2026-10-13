package m6_random_parallel;

import core.DataLoader;
import core.RetailDataStore;
import model.Activity;
import model.Product;

import java.util.Arrays;
import java.util.List;

public class M6Demo {
    public static void main(String[] args) {
        int[] values = {9, 4, 1, 7, 3, 8, 2, 6, 5};
        RandomizedQuickSort.sort(values, 42L);
        System.out.println("M6 randomized quicksort: " + Arrays.toString(values));

        int kth = RandomizedQuickSelect.selectKthSmallest(new int[]{12, 5, 7, 1, 9}, 2, 7L);
        System.out.println("M6 randomized quickselect k=2: " + kth);

        int[] sales = productSales();
        System.out.println("M6 parallel sum of sample sales: " + ParallelReducer.sum(sales, 4));
        System.out.println("M6 parallel max of sample sales: " + ParallelReducer.max(sales, 4));
        System.out.println("M6 Blelloch scan: " + Arrays.toString(BlellochScan.inclusivePrefixSum(new int[]{3, 1, 4, 1, 5})));

        RetailDataStore ds = new DataLoader("data").loadAll();
        List<Activity> sample = new ReservoirSampler<Activity>().sample(ds.getActivities(), 3, 99L);
        System.out.println("M6 reservoir activity sample size: " + sample.size());
    }

    private static int[] productSales() {
        RetailDataStore ds = new DataLoader("data").loadAll();
        int[] sales = new int[ds.getProducts().size()];
        for (int i = 0; i < sales.length; i++) {
            Product product = ds.getProducts().get(i);
            sales[i] = product.getSalesCount();
        }
        return sales;
    }
}
