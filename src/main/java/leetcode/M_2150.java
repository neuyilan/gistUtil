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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-02-19 13:05
 */
public class M_2150 {

  public static void main(String[] args) {
    int nums[] = new int[]{1,3,5,3};
    List<Integer> res = findLonely(nums);
    System.out.println(res);
    System.out.println(1<<10);
  }

  public static List<Integer> findLonely(int[] nums) {
    // first 排序；
    Arrays.sort(nums);

    List<Integer> result = new ArrayList<>();
    int before = 0;
    int after = 0;
    for (int i = 0; i < nums.length; i++) {
      int current = nums[i];
      if (i == 0) {
        after = nums[i + 1];
        if (Math.abs(after - current) > 1) {
          result.add(current);
        }
      } else if (i == nums.length - 1) {
        before = nums[i - 1];
        if (Math.abs(before - current) > 1) {
          result.add(current);
        }
      } else {
        before = nums[i - 1];
        after = nums[i + 1];
        if ((Math.abs(after - current) > 1) && ((Math.abs(before - current) > 1))) {
          result.add(current);
        }
      }
    }
    return result;
  }
}
