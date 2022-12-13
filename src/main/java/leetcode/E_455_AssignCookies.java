package leetcode;

import java.util.Arrays;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description 题目描述 有一群孩子和一堆饼干，每个孩子有一个饥饿度，每个饼干都有一个大小。每个孩子只能吃一个饼干，且只有饼干的大小不小于孩子的饥饿度时，这个孩子才能吃饱。求解最多有多少孩子
 * 可以吃饱。
 * <p>
 * 输入输出样例 输入两个数组，分别代表孩子的饥饿度和饼干的大小。输出最多有多少孩子可以吃饱的数量。 Input: [1,2], [1,2,3]
 * <p>
 * Output: 2
 * <p>
 * 在这个样例中，我们可以给两个孩子喂 [1,2]、[1,3]、[2,3] 这三种组合的任意一种。
 * @since 2022-03-13 13:19
 */

public class E_455_AssignCookies {

  public static int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);
    Arrays.sort(s);
    int index = 0;
    int count = 0;
    for (int i = 0; i < g.length; i++) {
      if (index > s.length - 1) {
        break;
      }
      if (s[index] >= g[i]) {
        count++;
        index++;
      } else {
        i--;
        index++;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    int[] hungry = {1, 2};
    int[] size = {1, 2, 3};
    int count = findContentChildren(hungry, size);
    System.out.println(count);
  }

}
