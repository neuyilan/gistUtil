package arraySort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-10-10 17:12
 */
public class ArraySort {

    public static void sort() {
        Random random = new Random();
        int totalCount = 10;
        int bound = 10_000_000;
        long st = 0;
        long lt = 0;

        for (int m = 0; m < totalCount; m++) {
            int sortArray[] = new int[1500];
            int longArray[] = new int[500000];
            for (int i = 0; i < sortArray.length; i++) {
                sortArray[i] = random.nextInt(bound);
            }

            for (int i = 0; i < longArray.length; i++) {
                longArray[i] = random.nextInt(bound);
            }

            long start = System.nanoTime();
            Arrays.sort(sortArray);
            long end = System.nanoTime();
            st += end - start;

            start = System.nanoTime();
            Arrays.sort(longArray);
            end = System.nanoTime();
            lt += end - start;

        }

        System.out.println(1500 + " size array sort cost: " + (st) / totalCount / 1000000. + " ms");
        System.out.println(500000 + " size array sort cost: " + (lt) / totalCount / 1000000. + " ms");
    }

    public static void main(String[] args) throws Exception {
        sort();
    }
}
