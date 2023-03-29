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

import java.util.Random;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-03-12 10:48
 */
public class M_528 {

  public static void main(String[] args) {
    int arr[] = new int[]{1, 3};
    M_528 solution = new M_528(arr);
    System.out.println(solution.pickIndex());
  }

  int[] sum;
  Random random = new Random();

  public M_528(int[] w) {
    sum = new int[w.length];
    sum[0] = w[0];
    for (int i = 1; i < w.length; i++) {
      sum[i] = sum[i - 1] + w[i];
    }
  }

  public int pickIndex() {
    int value = (int) (Math.random() * sum[sum.length - 1]) + 1;
//    int index = 0;
//    for (int i = 0; i < sum.length; i++) {
//      if (value <= sum[i]) {
//        index = i;
//        break;
//      }
//    }

    int left = 0;
    int right = sum.length - 1;
    while (left < right) {
      int index = (left + right) / 2;
      if ((sum[index] <= value) && (value <= sum[index + 1])) {
        return index;
      }
    }
    return 0;
  }
}
