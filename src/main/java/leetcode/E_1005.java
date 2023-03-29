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

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-02-25 19:44
 */
public class E_1005 {

  public static int largestSumAfterKNegations(int[] nums, int k) {
    Arrays.sort(nums);
    int count = k;
    for (int i = 0; i < nums.length; i++) {
      if (count == 0) {
        break;
      }
      if (nums[i] < 0) {
        nums[i] = -nums[i];
        count--;
      } else if (nums[i] == 0) {
        count = 0;
        break;
      }
    }

    for (int i = 0; i < nums.length; i++) {
      System.out.println(nums[i]);
    }

    count = count % 2;
    System.out.println("1111111=" + count);
    if (count == 0) {
      return Arrays.stream(nums).sum();
    }
    Arrays.sort(nums);
    nums[0] = -nums[0];
    System.out.println(nums);
    return Arrays.stream(nums).sum();
  }

  public static void main(String[] args) {
    int nums[] = new int[]{-4, -6, 9, -2, 2};
    int k = 4;
    int res = largestSumAfterKNegations(nums, k);
    System.out.println(res);
  }
}
