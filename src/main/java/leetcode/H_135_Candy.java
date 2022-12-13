package leetcode;

import java.util.Arrays;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2022-03-13 13:59
 */
public class H_135_Candy {

  public static int candy(int[] ratings) {
    // 每个孩子至少一个糖果
    int[] result = new int[ratings.length];
    Arrays.fill(result, 1);
    for (int i = 1; i < ratings.length; i++) {
      if ((ratings[i] > ratings[i - 1]) && (result[i] <= result[i - 1])) {
        result[i] = result[i - 1] + 1;
      }
    }
//    for (int j : result) {
//      System.out.print(j + "\t");
//    }
//    System.out.println();

    for (int i = ratings.length - 1; i > 0; i--) {
      if ((ratings[i - 1] > ratings[i]) && (result[i - 1] <= result[i])) {
        result[i - 1] = result[i] + 1;
      }
    }

    int minCount = 0;
    for (int j : result) {
//      System.out.print(j + "\t");
      minCount += j;
    }
//    System.out.println();
//    System.out.println(minCount);
    return minCount;
  }

  public static void main(String[] args) {
//    int ratings[] = {1, 0, 2};
//    int ratings[] = {1, 2, 2};
//    int ratings[] = {1, 3, 2, 2, 1};
    int ratings[] = {1, 2, 87, 87, 87, 2, 1};
    int minCount = candy(ratings);
  }
}
