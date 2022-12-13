/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description 题目描述 给定多个区间，计算让这些区间互不重叠所需要移除区间的最少个数。起止相连不算重叠。
 * <p>
 * 输入输出样例 输入是一个数组，数组由多个长度固定为 2 的数组组成，表示区间的开始和结尾。输出一个 整数，表示需要移除的区间数量。
 * <p>
 * Input: [[1,2], [2,4], [1,3]]
 * <p>
 * Output: 1
 * <p>
 * 在这个样例中，我们可以移除区间 [1,3]，使得剩余的区间 [[1,2], [2,4]] 互不重叠。
 * @since 2022-03-19 18:00
 */
public class M_435_NonOverlappingIntervals {

  public static int eraseOverlapIntervals(int[][] intervals) {
    int res = 0;
    Arrays.sort(intervals, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        if (o1[1] == o2[1]) {
          return o1[0] - o2[0];
        }
        return o1[1] - o2[1];
      }
    });

    int index = 0;
    for (int i = 0; i < intervals.length - 1; i++) {
      if (intervals[index][1] > intervals[i + 1][0]) {
        res++;
      } else {
        index = i + 1;
      }
    }

    System.out.println(res);
    return res;
  }

  public static void main(String[] args) {
//    int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
//    int[][] intervals = {{1, 2}, {1, 2}, {1, 2}};
    int[][] intervals = {{0, 2}, {1, 3}, {2, 4}, {3, 5}, {4, 6}};

    for (int i = 0; i < intervals.length; i++) {
      for (int j = 0; j < intervals[i].length; j++) {
        System.out.print(intervals[i][j] + "\t");
      }
      System.out.println();
    }

    eraseOverlapIntervals(intervals);
  }
}
