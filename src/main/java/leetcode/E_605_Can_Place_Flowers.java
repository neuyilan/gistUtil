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

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2022-03-19 18:24
 */
//https://leetcode.com/problems/can-place-flowers/
public class E_605_Can_Place_Flowers {

  public boolean canPlaceFlowers(int[] flowerbed, int n) {
    boolean res = false;
    int count = 0;
    for (int i = 0; i < flowerbed.length; i++) {
      if (flowerbed[i] == 0) {
        boolean leftEmpty = (i == 0 || (flowerbed[i - 1] == 0));
        boolean rightEmpty = (i == flowerbed.length - 1 || (flowerbed[i + 1] == 0));
        if (leftEmpty && rightEmpty) {
          flowerbed[i] = 1;
          count++;
        }
      }
    }
    if (count >= n) {
      res = true;
    }
    return res;
  }
}
